package com.jdroid.android.googleplay.publisher;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.Lists;
import com.google.api.services.androidpublisher.AndroidPublisher.Internalappsharingartifacts;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.ApksListResponse;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Bundle;
import com.google.api.services.androidpublisher.model.BundlesListResponse;
import com.google.api.services.androidpublisher.model.ImagesUploadResponse;
import com.google.api.services.androidpublisher.model.InternalAppSharingArtifact;
import com.google.api.services.androidpublisher.model.Listing;
import com.google.api.services.androidpublisher.model.LocalizedText;
import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TrackRelease;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Helper class to initialize the publisher APIs client library.
 * <p>
 * Before making any calls to the API through the client library you need to call the
 * {@link GooglePlayPublisher#init(AppContext)} method. This will run all precondition checks for for client id and
 * secret setup properly in resources/client_secrets.json and authorize this client against the API.
 * </p>
 */
public class GooglePlayPublisher extends AbstractGooglePlayPublisher {

	private static final String APK_MIME_TYPE = "application/vnd.android.package-archive";
	private static final String BUNDLE_MIME_TYPE = "application/octet-stream";
	private static final String DEOBFUSCATION_MIME_TYPE = "application/octet-stream";
	private static final String DEOBFUSCATION_FILE_TYPE = "proguard";

	/**
	 * Retrieve all the apks for a given app.
	 *
	 * @param app
	 */
	public static List<Apk> getApks(App app) {
		try {
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			// Get a list of apks.
			ApksListResponse apksResponse = edits.apks().list(app.getApplicationId(), edit.getId()).execute();
			
			return apksResponse.getApks() != null ? apksResponse.getApks() : Lists.newArrayList();
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Retrieve all the bundles for a given app.
	 *
	 * @param app
	 */
	public static List<Bundle> getBundles(App app) {
		try {
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			// Get a list of bundles.
			BundlesListResponse bundlesListResponse = edits.bundles().list(app.getApplicationId(), edit.getId()).execute();
			
			return bundlesListResponse.getBundles() != null ? bundlesListResponse.getBundles() : Lists.newArrayList();
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void verifyMetadata(App app) {
		log("Verifying the content to upload to Google Play on " + app.getAppContext().getMetadataPath());
		
		for (LocaleListing each : app.getLocaleListings()) {
			log("Verifying locale " + each.getLanguageTag());
			app.getTitle(each);
			app.getFullDescription(each);
			app.getShortDescription(each);
			app.getFullDescription(each);
			app.getFeatureGraphic(each);
			app.getPromoGraphic(each);
			app.getVideo(each);
			app.getHighResolutionIcon(each);
			app.getPhoneScreenshots(each);
			app.getSevenInchScreenshots(each);
			app.getTenInchScreenshots(each);
			app.getTvBanner(each);
			app.getTvScreenshots(each);
			app.getWearScreenshots(each);
			app.getReleaseNotes(each, 0);
			log("Successful verification on locale " + each.getLanguageTag());
		}
	}
	
	public static void publishMetadata(App app) {
		try {

			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			// Update listing for each locale of the application.
			for (LocaleListing each : app.getLocaleListings()) {

				String localeString = each.getLanguageTag();

				Listing listing = new Listing();
				listing.setTitle(app.getTitle(each));
				listing.setFullDescription(app.getFullDescription(each));
				listing.setShortDescription(app.getShortDescription(each));
				listing.setVideo(app.getVideo(each));
				Listing updatedListing = edits.listings().update(app.getApplicationId(), edit.getId(), localeString, listing).execute();
				log(String.format("Created new " + localeString + " app listing with title: %s", updatedListing.getTitle()));

				// Feature Graphic
				AbstractInputStreamContent featureGraphic = app.getFeatureGraphic(each);
				ImagesUploadResponse response = edits.images().upload(app.getApplicationId(), edit.getId(),
						localeString, ImageType.FEATURE_GRAPHIC.getKey(), featureGraphic).execute();
				log(String.format("Feature graphic %s has been updated.", response.getImage()));

				// Promo Graphic
				AbstractInputStreamContent promoGraphic = app.getPromoGraphic(each);
				if (promoGraphic != null) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.PROMO_GRAPHIC.getKey(), promoGraphic).execute();
					log(String.format("Promo graphic %s has been updated.", response.getImage()));
				}

				// High Resolution Icon
				AbstractInputStreamContent highResolutionIcon = app.getHighResolutionIcon(each);
				response = edits.images().upload(app.getApplicationId(), edit.getId(),
						localeString, ImageType.ICON.getKey(), highResolutionIcon).execute();
				log(String.format("High resolution icon %s has been updated.", response.getImage()));
				
				// High Resolution Icon
				AbstractInputStreamContent tvBanner = app.getTvBanner(each);
				if (tvBanner != null) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.TV_BANNER.getKey(), tvBanner).execute();
					log(String.format("Tv banner %s has been updated.", response.getImage()));
				}

				// Phone Screenshots
				edits.images().deleteall(app.getApplicationId(), edit.getId(), localeString, ImageType.PHONE_SCREENSHOTS.getKey()).execute();
				log("Phone screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getPhoneScreenshots(each)) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.PHONE_SCREENSHOTS.getKey(), content).execute();
					log(String.format("Phone screenshot %s has been updated.", response.getImage()));
				}

				// 7-inch Screenshots
				edits.images().deleteall(app.getApplicationId(), edit.getId(), localeString, ImageType.SEVEN_INCH_SCREENSHOTS.getKey()).execute();
				log("Seven inch screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getSevenInchScreenshots(each)) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.SEVEN_INCH_SCREENSHOTS.getKey(), content).execute();
					log(String.format("Seven inch screenshot %s has been updated.", response.getImage()));
				}

				// 10-inch Screenshots
				edits.images().deleteall(app.getApplicationId(), edit.getId(), localeString, ImageType.TEN_INCH_SCREENSHOTS.getKey()).execute();
				log("Ten inch screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getTenInchScreenshots(each)) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.TEN_INCH_SCREENSHOTS.getKey(), content).execute();
					log(String.format("Ten inch screenshot %s has been updated.", response.getImage()));
				}
				
				// Tv Screenshots
				edits.images().deleteall(app.getApplicationId(), edit.getId(), localeString, ImageType.TV_SCREENSHOTS.getKey()).execute();
				log("Tv screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getTvScreenshots(each)) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.TV_SCREENSHOTS.getKey(), content).execute();
					log(String.format("Tv screenshot %s has been updated.", response.getImage()));
				}
				
				// Wear Screenshots
				edits.images().deleteall(app.getApplicationId(), edit.getId(), localeString, ImageType.WEAR_SCREENSHOTS.getKey()).execute();
				log("Wear screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getWearScreenshots(each)) {
					response = edits.images().upload(app.getApplicationId(), edit.getId(),
							localeString, ImageType.WEAR_SCREENSHOTS.getKey(), content).execute();
					log(String.format("Wear screenshot %s has been updated.", response.getImage()));
				}
			}

			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static void setDefaultUserFraction(App app, Edits edits, String editId) {
		if (app.getAppContext().getTrackType().equals(TrackType.PRODUCTION)) {
			if (app.getAppContext().getUserFraction() == null) {
				TrackRelease currentRolloutTrackRelease = getInProgressRollout(app, edits, editId);
				if (currentRolloutTrackRelease != null) {
					app.getAppContext().setUserFraction(currentRolloutTrackRelease.getUserFraction());
				}
			} else {
				if (app.getAppContext().getUserFraction().equals(1D)) {
					app.getAppContext().setUserFraction(null);
				}
			}
		} else {
			app.getAppContext().setUserFraction(null);
		}
	}
	
	public static void publishApk(App app) {
		try {
			
			if (Strings.isNullOrEmpty(app.getAppContext().getApkDir()) && Strings.isNullOrEmpty(app.getAppContext().getApkPath())) {
				throw new UnexpectedException("apkDir and apkPath cannot be both null or empty!");
			}
			
			if (app.getAppContext().getTrackType() == null) {
				throw new UnexpectedException("trackType cannot be null");
			}

			if (app.getAppContext().isDeobfuscationFileUploadEnabled() && app.getAppContext().getDeobfuscationFilePath() == null) {
				throw new UnexpectedException("deobfuscationFilePath cannot be null");
			}
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			// Upload new apk to developer console
			if (Strings.isNullOrEmpty(app.getAppContext().getApkPath())) {
				if (!Strings.isNullOrEmpty(app.getAppContext().getApkDir())) {
					Supplier<Stream<Path>> streamSupplier = () -> {
						try {
							return Files.list(Paths.get(app.getAppContext().getApkDir())).filter(new Predicate<Path>() {
								@Override
								public boolean test(Path path) {
									return path.getFileName().toString().endsWith(".apk") && !path.getFileName().toString().endsWith("unaligned.apk");
								}
							});
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					};
					long count = streamSupplier.get().count();
					if (count == 1) {
						app.getAppContext().setApkPath(streamSupplier.get().findAny().get().toAbsolutePath().toString());
					} else if (count == 0) {
						throw new UnexpectedException("APK not found");
					} else {
						throw new UnexpectedException("More than one APK found at the specified directory");
					}
				}
			}
			
			AbstractInputStreamContent apkFile = new FileContent(APK_MIME_TYPE, new File(app.getAppContext().getApkPath()));
			Apk apk = edits.apks().upload(app.getApplicationId(), edit.getId(), apkFile).execute();
			log(String.format("Version code %d has been uploaded", apk.getVersionCode()));
			
			setDefaultUserFraction(app, edits, edit.getId());
			
			// Assign apk to track.
			Track track = new Track();
			track.setTrack(app.getAppContext().getTrackType().getKey());
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setName(app.getAppContext().getReleaseName());
			trackRelease.setVersionCodes(Collections.singletonList(apk.getVersionCode().longValue()));
			
			TrackReleaseStatus trackReleaseStatus;
			if (app.getAppContext().isDraft()) {
				trackReleaseStatus = TrackReleaseStatus.DRAFT;
			} else if (app.getAppContext().getUserFraction() != null) {
				trackReleaseStatus = TrackReleaseStatus.IN_PROGRESS;
			} else {
				trackReleaseStatus = TrackReleaseStatus.COMPLETED;
			}
			trackRelease.setStatus(trackReleaseStatus.getKey());
			trackRelease.setUserFraction(app.getAppContext().getUserFraction());
			
			List<LocalizedText> releaseNotes = Lists.newArrayList();
			for (LocaleListing each : app.getLocaleListings()) {
				String releaseNoteText = app.getReleaseNotes(each, apk.getVersionCode());
				if (StringUtils.isNotBlank(releaseNoteText)) {
					LocalizedText releaseNote = new LocalizedText();
					releaseNote.setLanguage(each.getLanguageTag());
					releaseNote.setText(releaseNoteText);
					releaseNotes.add(releaseNote);
				}
			}
			trackRelease.setReleaseNotes(releaseNotes);
			track.setReleases(Collections.singletonList(trackRelease));
			
			Track updatedTrack = edits.tracks().update(app.getApplicationId(), edit.getId(), track.getTrack(), track).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));

			// Upload deobfuscation file
			if (app.getAppContext().isDeobfuscationFileUploadEnabled()) {
				File deobfuscationFilePath = new File(app.getAppContext().getDeobfuscationFilePath());
				if (deobfuscationFilePath.exists()) {
					AbstractInputStreamContent deobfuscationFile = new FileContent(DEOBFUSCATION_MIME_TYPE, deobfuscationFilePath);
					edits.deobfuscationfiles().upload(app.getApplicationId(), edit.getId(), apk.getVersionCode(), DEOBFUSCATION_FILE_TYPE, deobfuscationFile);
					log("Adding deobfuscation file " + app.getAppContext().getDeobfuscationFilePath());
				} else {
					throw new RuntimeException(deobfuscationFilePath + " doesn't exist.");
				}
			}
			
			// Commit changes for edit.
			commitEdit(app, edits, edit);
		} catch (GoogleJsonResponseException ex) {
			if (!app.getAppContext().failOnApkUpgradeVersionConflict() && ex.getDetails().getCode() == 403 && ex.getDetails().getMessage().equals("APK specifies a version code that has already been used.")) {
				log("WARNING | apkUpgradeVersionConflict: APK specifies a version code that has already been used.");
			} else {
				throw new RuntimeException(ex.getDetails().getMessage(), ex);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void publishBundle(App app) {
		try {
			
			if (Strings.isNullOrEmpty(app.getAppContext().getBundleDir()) && Strings.isNullOrEmpty(app.getAppContext().getBundlePath())) {
				throw new UnexpectedException("bundleDir and bundlePath cannot be both null or empty!");
			}
			
			if (app.getAppContext().getTrackType() == null) {
				throw new UnexpectedException("trackType cannot be null");
			}

			if (app.getAppContext().isDeobfuscationFileUploadEnabled() && app.getAppContext().getDeobfuscationFilePath() == null) {
				throw new UnexpectedException("deobfuscationFilePath cannot be null");
			}
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			AbstractInputStreamContent bundleFile = getBundleFile(app);
			Bundle bundle = edits.bundles().upload(app.getApplicationId(), edit.getId(), bundleFile).execute();
			log(String.format("Version code %d has been uploaded", bundle.getVersionCode()));

			setDefaultUserFraction(app, edits, edit.getId());
			
			// Assign bundle to track.
			Track track = new Track();
			track.setTrack(app.getAppContext().getTrackType().getKey());
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setName(app.getAppContext().getReleaseName());
			trackRelease.setVersionCodes(Collections.singletonList(bundle.getVersionCode().longValue()));
			
			TrackReleaseStatus trackReleaseStatus;
			if (app.getAppContext().isDraft()) {
				trackReleaseStatus = TrackReleaseStatus.DRAFT;
			} else if (app.getAppContext().getUserFraction() != null) {
				trackReleaseStatus = TrackReleaseStatus.IN_PROGRESS;
			} else {
				trackReleaseStatus = TrackReleaseStatus.COMPLETED;
			}
			trackRelease.setStatus(trackReleaseStatus.getKey());
			trackRelease.setUserFraction(app.getAppContext().getUserFraction());
			
			List<LocalizedText> releaseNotes = Lists.newArrayList();
			for (LocaleListing each : app.getLocaleListings()) {
				String releaseNoteText = app.getReleaseNotes(each, bundle.getVersionCode());
				if (StringUtils.isNotBlank(releaseNoteText)) {
					LocalizedText releaseNote = new LocalizedText();
					releaseNote.setLanguage(each.getLanguageTag());
					releaseNote.setText(releaseNoteText);
					releaseNotes.add(releaseNote);
					log("Adding release notes for locale " + each.getLanguageTag());
				}
			}
			trackRelease.setReleaseNotes(releaseNotes);
			track.setReleases(Collections.singletonList(trackRelease));
			
			Track updatedTrack = edits.tracks().update(app.getApplicationId(), edit.getId(), track.getTrack(), track).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));

			// Upload deobfuscation file
			if (app.getAppContext().isDeobfuscationFileUploadEnabled()) {
				File deobfuscationFilePath = new File(app.getAppContext().getDeobfuscationFilePath());
				if (deobfuscationFilePath.exists()) {
					AbstractInputStreamContent deobfuscationFile = new FileContent(DEOBFUSCATION_MIME_TYPE, deobfuscationFilePath);
					edits.deobfuscationfiles().upload(app.getApplicationId(), edit.getId(), bundle.getVersionCode(), DEOBFUSCATION_FILE_TYPE, deobfuscationFile);
					log("Adding deobfuscation file " + app.getAppContext().getDeobfuscationFilePath());
				} else {
					throw new RuntimeException(deobfuscationFilePath + " doesn't exist.");
				}
			}

			// Commit changes for edit.
			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private static TrackRelease getHaltedRollout(App app, Edits edits, String editId) {
		Track currentRolloutTrack = getTrack(app, TrackType.PRODUCTION, edits, editId);
		if (currentRolloutTrack != null && currentRolloutTrack.getReleases() != null) {
			for (TrackRelease trackRelease : currentRolloutTrack.getReleases()) {
				if (trackRelease.getStatus().equals(TrackReleaseStatus.HALTED.getKey())) {
					return trackRelease;
				}
			}
		}
		return null;
	}
	
	private static TrackRelease getInProgressRollout(App app, Edits edits, String editId) {
		Track currentRolloutTrack = getTrack(app, TrackType.PRODUCTION, edits, editId);
		if (currentRolloutTrack != null && currentRolloutTrack.getReleases() != null) {
			for (TrackRelease trackRelease : currentRolloutTrack.getReleases()) {
				if (trackRelease.getStatus().equals(TrackReleaseStatus.IN_PROGRESS.getKey())) {
					return trackRelease;
				}
			}
		}
		return null;
	}
	
	private static Track getTrack(App app, TrackType trackType, Edits edits, String editId) {
		try {
			return edits.tracks().get(app.getApplicationId(), editId, trackType.getKey()).execute();
		} catch (GoogleJsonResponseException ex) {
			if (ex.getDetails().getCode() == 404) {
				return null;
			} else {
				throw new RuntimeException(ex.getDetails().getMessage(), ex);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void increaseStagedRollout(App app) {
		try {
			
			if (app.getAppContext().getUserFraction() == null) {
				throw new RuntimeException("userFraction cannot be null");
			}
			
			if (app.getAppContext().getUserFraction().equals(1D)) {
				completeStagedRollout(app);
			} else {
				app.getAppContext().setTrackType(TrackType.PRODUCTION);
				
				Edits edits = init(app.getAppContext()).edits();
				AppEdit edit = createEdit(app, edits);
				
				Track track = new Track();
				track.setTrack(TrackType.PRODUCTION.getKey());
				
				TrackRelease currentRolloutRelease = getInProgressRollout(app, edits, edit.getId());
				if (currentRolloutRelease == null) {
					throw new UnexpectedException("No in progress staged rollout release found");
				}
				
				TrackRelease trackRelease = new TrackRelease();
				trackRelease.setStatus(TrackReleaseStatus.IN_PROGRESS.getKey());
				trackRelease.setUserFraction(app.getAppContext().getUserFraction());
				trackRelease.setVersionCodes(currentRolloutRelease.getVersionCodes());
				track.setReleases(Collections.singletonList(trackRelease));
				
				Track updatedTrack = edits.tracks().patch(app.getApplicationId(), edit.getId(), track.getTrack(), track).execute();
				log(String.format("Track %s has been updated.", updatedTrack.getTrack()));
				
				// Commit changes for edit.
				commitEdit(app, edits, edit);
			}
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void haltStagedRollout(App app) {
		try {
			
			app.getAppContext().setTrackType(TrackType.PRODUCTION);
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			Track track = new Track();
			track.setTrack(TrackType.PRODUCTION.getKey());
			
			TrackRelease currentRolloutRelease = getInProgressRollout(app, edits, edit.getId());
			if (currentRolloutRelease == null) {
				throw new UnexpectedException("No in progress staged rollout release found");
			}
			
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setStatus(TrackReleaseStatus.HALTED.getKey());
			trackRelease.setVersionCodes(currentRolloutRelease.getVersionCodes());
			trackRelease.setUserFraction(currentRolloutRelease.getUserFraction());
			track.setReleases(Collections.singletonList(trackRelease));
			
			Track updatedTrack = edits.tracks().patch(app.getApplicationId(), edit.getId(), track.getTrack(), track).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void resumeStagedRollout(App app) {
		try {
			
			app.getAppContext().setTrackType(TrackType.PRODUCTION);
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			Track track = new Track();
			track.setTrack(TrackType.PRODUCTION.getKey());
			
			TrackRelease currentRolloutRelease = getHaltedRollout(app, edits, edit.getId());
			if (currentRolloutRelease == null) {
				throw new UnexpectedException("No halted staged rollout release found");
			}
			
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setStatus(TrackReleaseStatus.IN_PROGRESS.getKey());
			trackRelease.setVersionCodes(currentRolloutRelease.getVersionCodes());
			trackRelease.setUserFraction(currentRolloutRelease.getUserFraction());
			track.setReleases(Collections.singletonList(trackRelease));
			
			Track updatedTrack = edits.tracks().patch(app.getApplicationId(), edit.getId(), track.getTrack(), track).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void promoteFromInternalToAlpha(App app) {
		promote(app, TrackType.INTERNAL, TrackType.ALPHA);
	}
	
	public static void promoteFromInternalToBeta(App app) {
		promote(app, TrackType.INTERNAL, TrackType.BETA);
	}
	
	public static void promoteFromInternalToProduction(App app) {
		promote(app, TrackType.INTERNAL, TrackType.PRODUCTION);
	}
	
	public static void promoteFromAlphaToBeta(App app) {
		promote(app, TrackType.ALPHA, TrackType.BETA);
	}
	
	public static void promoteFromAlphaToProduction(App app) {
		promote(app, TrackType.ALPHA, TrackType.PRODUCTION);
	}
	
	public static void promoteFromBetaToProduction(App app) {
		promote(app, TrackType.BETA, TrackType.PRODUCTION);
	}
	
	private static void promote(App app, TrackType fromTrackType, TrackType toTrackType) {
		try {
			
			app.getAppContext().setTrackType(toTrackType);
			
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			setDefaultUserFraction(app, edits, edit.getId());
			
			// Add APKs/bundles to toTrackType track
			Track fromTrack = edits.tracks().get(app.getApplicationId(), edit.getId(), fromTrackType.getKey()).execute();
			
			Track toTrack = new Track();
			toTrack.setTrack(toTrackType.getKey());
			
			List<TrackRelease> toTrackReleases = Lists.newArrayList();
			for (TrackRelease fromTrackRelease : fromTrack.getReleases()) {
				if (app.getAppContext().getReleaseName() == null || app.getAppContext().getReleaseName().equals(fromTrackRelease.getName())) {
					TrackRelease toTrackRelease = new TrackRelease();
					toTrackRelease.setName(fromTrackRelease.getName());
					toTrackRelease.setUserFraction(app.getAppContext().getUserFraction());
					toTrackRelease.setVersionCodes(fromTrackRelease.getVersionCodes());
					
					TrackReleaseStatus trackReleaseStatus;
					if (app.getAppContext().getUserFraction() != null) {
						trackReleaseStatus = TrackReleaseStatus.IN_PROGRESS;
					} else {
						trackReleaseStatus = TrackReleaseStatus.COMPLETED;
					}
					toTrackRelease.setStatus(trackReleaseStatus.getKey());
					toTrackRelease.setReleaseNotes(fromTrackRelease.getReleaseNotes());
					toTrackReleases.add(toTrackRelease);
				}
			}
			toTrack.setReleases(toTrackReleases);
			
			Track updatedTrack = edits.tracks().update(app.getApplicationId(), edit.getId(), toTrack.getTrack(), toTrack).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static void completeStagedRollout(App app) {
		try {
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			
			TrackRelease currentRolloutRelease = getInProgressRollout(app, edits, edit.getId());
			if (currentRolloutRelease == null) {
				throw new UnexpectedException("No in progress staged rollout release found");
			}
			currentRolloutRelease.setStatus(TrackReleaseStatus.COMPLETED.getKey());
			currentRolloutRelease.setUserFraction(null);
			
			Track productionTrack = new Track();
			productionTrack.setTrack(TrackType.PRODUCTION.getKey());
			productionTrack.setReleases(Collections.singletonList(currentRolloutRelease));
			
			Track updatedTrack = edits.tracks().update(app.getApplicationId(), edit.getId(), productionTrack.getTrack(), productionTrack).execute();
			log(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, edit);
			
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static List<Track> getTracks(App app) {
		try {
			Edits edits = init(app.getAppContext()).edits();
			AppEdit edit = createEdit(app, edits);
			return edits.tracks().list(app.getApplicationId(), edit.getId()).execute().getTracks();
		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static Track getTrack(App app) {
		Edits edits = init(app.getAppContext()).edits();
		AppEdit edit = createEdit(app, edits);
		return getTrack(app, app.getAppContext().getTrackType(), edits, edit.getId());
	}
	
	public static TrackRelease getInternalTrackRelease(App app) {
		app.getAppContext().setTrackType(TrackType.INTERNAL);
		List<TrackRelease> trackReleases = getTrackReleases(app);
		return trackReleases.isEmpty() ? null : trackReleases.get(0);
	}
	
	public static List<TrackRelease> getAlphaTrackReleases(App app) {
		app.getAppContext().setTrackType(TrackType.ALPHA);
		return getTrackReleases(app);
	}
	
	public static TrackRelease getBetaTrackRelease(App app) {
		app.getAppContext().setTrackType(TrackType.BETA);
		List<TrackRelease> trackReleases = getTrackReleases(app);
		return trackReleases.isEmpty() ? null : trackReleases.get(0);
	}
	
	public static TrackRelease getCompletedProductionTrackRelease(App app) {
		app.getAppContext().setTrackType(TrackType.PRODUCTION);
		for (TrackRelease trackRelease : getTrackReleases(app)) {
			if (trackRelease.getStatus().equals(TrackReleaseStatus.COMPLETED.getKey())) {
				return trackRelease;
			}
		}
		return null;
	}
	
	public static TrackRelease getStagedRolloutTrackRelease(App app) {
		app.getAppContext().setTrackType(TrackType.PRODUCTION);
		for (TrackRelease trackRelease : getTrackReleases(app)) {
			if (trackRelease.getStatus().equals(TrackReleaseStatus.IN_PROGRESS.getKey())) {
				return trackRelease;
			}
		}
		return null;
	}
	
	public static TrackRelease getHaltedProductionTrackRelease(App app) {
		app.getAppContext().setTrackType(TrackType.PRODUCTION);
		for (TrackRelease trackRelease : getTrackReleases(app)) {
			if (trackRelease.getStatus().equals(TrackReleaseStatus.HALTED.getKey())) {
				return trackRelease;
			}
		}
		return null;
	}
	
	private static List<TrackRelease> getTrackReleases(App app) {
		Track track = getTrack(app);
		if (track != null) {
			return track.getReleases() != null ? track.getReleases() : Lists.newArrayList();
		} else {
			return Lists.newArrayList();
		}
	}

	public static InternalAppSharingArtifact uploadBundleToInternalAppSharing(App app) {
		try {

			if (Strings.isNullOrEmpty(app.getAppContext().getBundleDir()) && Strings.isNullOrEmpty(app.getAppContext().getBundlePath())) {
				throw new UnexpectedException("bundleDir and bundlePath cannot be both null or empty!");
			}

			AbstractInputStreamContent bundleFile = getBundleFile(app);

			Internalappsharingartifacts internalAppSharingArtifacts = init(app.getAppContext()).internalappsharingartifacts();
			Internalappsharingartifacts.Uploadbundle uploadBundle = internalAppSharingArtifacts.uploadbundle(app.getApplicationId(), bundleFile);

			if (app.getAppContext().isDryRun()) {
				log("Dry run mode enabled. Bundle not uploaded to internal app sharing");
				return null;
			} else {
				InternalAppSharingArtifact internalAppSharingArtifact = uploadBundle.execute();
				log("Bundle uploaded to internal app sharing:");
				log("- Certificate Fingerprint: " + internalAppSharingArtifact.getCertificateFingerprint());
				log("- Download Url: " + internalAppSharingArtifact.getDownloadUrl());
				return internalAppSharingArtifact;
			}

		} catch (GoogleJsonResponseException ex) {
			throw new RuntimeException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private static AbstractInputStreamContent getBundleFile(App app) {
		// Upload new bundle to developer console
		if (Strings.isNullOrEmpty(app.getAppContext().getBundlePath())) {
			if (!Strings.isNullOrEmpty(app.getAppContext().getBundleDir())) {
				Supplier<Stream<Path>> streamSupplier = () -> {
					try {
						return Files.list(Paths.get(app.getAppContext().getBundleDir())).filter(new Predicate<Path>() {
							@Override
							public boolean test(Path path) {
								return path.getFileName().toString().endsWith(".aab") && !path.getFileName().toString().endsWith("unaligned.aab");
							}
						});
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				};
				long count = streamSupplier.get().count();
				if (count == 1) {
					app.getAppContext().setBundlePath(streamSupplier.get().findAny().get().toAbsolutePath().toString());
				} else if (count == 0) {
					throw new UnexpectedException("Bundle not found");
				} else {
					throw new UnexpectedException("More than one Bundle found at the specified path");
				}
			}
		}

		return new FileContent(BUNDLE_MIME_TYPE, new File(app.getAppContext().getBundlePath()));
	}
}
