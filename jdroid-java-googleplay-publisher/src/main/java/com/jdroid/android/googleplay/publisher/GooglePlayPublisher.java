package com.jdroid.android.googleplay.publisher;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.Lists;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apks.Upload;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Commit;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Images;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Images.Deleteall;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Insert;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.ApksListResponse;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Bundle;
import com.google.api.services.androidpublisher.model.BundlesListResponse;
import com.google.api.services.androidpublisher.model.ImagesUploadResponse;
import com.google.api.services.androidpublisher.model.Listing;
import com.google.api.services.androidpublisher.model.LocalizedText;
import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TrackRelease;
import com.google.api.services.androidpublisher.model.TracksListResponse;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Helper class to initialize the publisher APIs client library.
 * <p>
 * Before making any calls to the API through the client library you need to call the
 * {@link GooglePlayPublisher#init(AppContext)} method. This will run all precondition checks for for client id and
 * secret setup properly in resources/client_secrets.json and authorize this client against the API.
 * </p>
 */
public class GooglePlayPublisher {

	private static final String APK_MIME_TYPE = "application/vnd.android.package-archive";
	private static final String BUNDLE_MIME_TYPE = "application/octet-stream";
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	public static final double DEFAULT_USER_FRACTION = 0.005;
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;
	
	/**
	 * Performs all necessary setup steps for running requests against the API.
	 * 
	 * @param appContext the {@link AppContext}
	 * @return the {@link AndroidPublisher} service
	 */
	private static AndroidPublisher init(AppContext appContext) {

		if (StringUtils.isEmpty(appContext.getApplicationId())) {
			throw new UnexpectedException("The application id is required");
		}
		
		try {
			
			if (HTTP_TRANSPORT == null) {
				HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			}
			
			// Authorization.
			Credential credential = authorizeWithServiceAccount(appContext);
			
			// Set up and return API client.
			return new AndroidPublisher.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
				appContext.getApplicationId()).build();
		} catch (GeneralSecurityException | IOException e) {
			throw new UnexpectedException(e);
		}
	}
	
	private static Credential authorizeWithServiceAccount(AppContext appContext) throws IOException {

		if (StringUtils.isEmpty(appContext.getPrivateKeyJsonFilePath())) {
			throw new UnexpectedException("The private key json file path is required");
		}
		
		InputStream serviceAccountStream = new FileInputStream(appContext.getPrivateKeyJsonFilePath());
		GoogleCredential credential = GoogleCredential.fromStream(serviceAccountStream, HTTP_TRANSPORT, JSON_FACTORY);
		return credential.createScoped(Collections.singleton(AndroidPublisherScopes.ANDROIDPUBLISHER));
	}
	
	/**
	 * Retrieve all the apks for a given app.
	 *
	 * @param app
	 */
	public static List<Apk> getApks(App app) {
		try {

			AppContext appContext = app.getAppContext();
			// Create the API service.
			AndroidPublisher service = init(appContext);
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit appEdit = editRequest.execute();
			
			// Get a list of apks.
			ApksListResponse apksResponse = edits.apks().list(app.getApplicationId(), appEdit.getId()).execute();
			
			return apksResponse.getApks() != null ? apksResponse.getApks() : Lists.newArrayList();
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while getting APKs list", ex);
		}
	}
	
	/**
	 * Retrieve all the bundles for a given app.
	 *
	 * @param app
	 */
	public static List<Bundle> getBundles(App app) {
		try {

			AppContext appContext = app.getAppContext();
			// Create the API service.
			AndroidPublisher service = init(appContext);
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit appEdit = editRequest.execute();
			
			// Get a list of bundles.
			BundlesListResponse bundlesListResponse = edits.bundles().list(app.getApplicationId(), appEdit.getId()).execute();
			
			return bundlesListResponse.getBundles() != null ? bundlesListResponse.getBundles() : Lists.newArrayList();
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while getting bundle list", ex);
		}
	}
	
	public static void verifyMetadata(App app) {
		System.out.println(("Verifying the content to upload to Google Play on " + app.getAppContext().getMetadataPath()));
		
		for (LocaleListing each : app.getLocaleListings()) {
			System.out.println(("Verifying locale " + each.getLanguageTag()));
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
		}
	}
	
	public static void publishMetadata(App app) {
		try {

			AppContext appContext = app.getAppContext();

			// Create the API service.
			AndroidPublisher service = init(appContext);
			Edits edits = service.edits();
			
			// Create an edit to update listing for application.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Update listing for each locale of the application.
			for (LocaleListing each : app.getLocaleListings()) {

				String localeString = each.getLanguageTag();

				Listing listing = new Listing();
				listing.setTitle(app.getTitle(each));
				listing.setFullDescription(app.getFullDescription(each));
				listing.setShortDescription(app.getShortDescription(each));
				listing.setVideo(app.getVideo(each));
				Edits.Listings.Update updateListingsRequest = edits.listings().update(app.getApplicationId(),
						editId, localeString, listing);
				Listing updatedListing = updateListingsRequest.execute();
				System.out.println(String.format("Created new " + localeString + " app listing with title: %s",
						updatedListing.getTitle()));

				// Feature Graphic
				AbstractInputStreamContent featureGraphic = app.getFeatureGraphic(each);
				Images.Upload uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
						localeString, ImageType.FEATURE_GRAPHIC.getKey(), featureGraphic);
				ImagesUploadResponse response = uploadImageRequest.execute();
				System.out.println(String.format("Feature graphic %s has been updated.", response.getImage()));

				// Promo Graphic
				AbstractInputStreamContent promoGraphic = app.getPromoGraphic(each);
				if (promoGraphic != null) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.PROMO_GRAPHIC.getKey(), promoGraphic);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Promo graphic %s has been updated.", response.getImage()));
				}

				// High Resolution Icon
				AbstractInputStreamContent highResolutionIcon = app.getHighResolutionIcon(each);
				uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
						localeString, ImageType.ICON.getKey(), highResolutionIcon);
				response = uploadImageRequest.execute();
				System.out.println(String.format("High resolution icon %s has been updated.", response.getImage()));
				
				// High Resolution Icon
				AbstractInputStreamContent tvBanner = app.getTvBanner(each);
				if (tvBanner != null) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.TV_BANNER.getKey(), tvBanner);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Tv banner %s has been updated.", response.getImage()));
				}

				// Phone Screenshots
				Deleteall deleteallRequest = edits.images().deleteall(app.getApplicationId(), editId,
						localeString, ImageType.PHONE_SCREENSHOTS.getKey());
				deleteallRequest.execute();
				System.out.println("Phone screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getPhoneScreenshots(each)) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.PHONE_SCREENSHOTS.getKey(), content);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Phone screenshot %s has been updated.", response.getImage()));
				}

				// 7-inch Screenshots
				deleteallRequest = edits.images().deleteall(app.getApplicationId(), editId,
						localeString, ImageType.SEVEN_INCH_SCREENSHOTS.getKey());
				deleteallRequest.execute();
				System.out.println("Seven inch screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getSevenInchScreenshots(each)) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.SEVEN_INCH_SCREENSHOTS.getKey(), content);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Seven inch screenshot %s has been updated.", response.getImage()));
				}

				// 10-inch Screenshots
				deleteallRequest = edits.images().deleteall(app.getApplicationId(), editId,
						localeString, ImageType.TEN_INCH_SCREENSHOTS.getKey());
				deleteallRequest.execute();
				System.out.println("Ten inch screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getTenInchScreenshots(each)) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.TEN_INCH_SCREENSHOTS.getKey(), content);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Ten inch screenshot %s has been updated.", response.getImage()));
				}
				
				// Tv Screenshots
				deleteallRequest = edits.images().deleteall(app.getApplicationId(), editId,
						localeString, ImageType.TV_SCREENSHOTS.getKey());
				deleteallRequest.execute();
				System.out.println("Tv screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getTvScreenshots(each)) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.TV_SCREENSHOTS.getKey(), content);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Tv screenshot %s has been updated.", response.getImage()));
				}
				
				// Wear Screenshots
				deleteallRequest = edits.images().deleteall(app.getApplicationId(), editId,
						localeString, ImageType.WEAR_SCREENSHOTS.getKey());
				deleteallRequest.execute();
				System.out.println("Wear screenshots has been deleted.");
				for (AbstractInputStreamContent content : app.getWearScreenshots(each)) {
					uploadImageRequest = edits.images().upload(app.getApplicationId(), editId,
							localeString, ImageType.WEAR_SCREENSHOTS.getKey(), content);
					response = uploadImageRequest.execute();
					System.out.println(String.format("Wear screenshot %s has been updated.", response.getImage()));
				}
			}

			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while updating listing", ex);
		}
	}
	
	public static void publishApk(App app) {
		try {
			
			if (Strings.isNullOrEmpty(app.getAppContext().getApkPath())) {
				throw new UnexpectedException("apkPath cannot be null or empty!");
			}
			
			if (app.getAppContext().getTrackType() == null) {
				throw new UnexpectedException("trackType cannot be null or empty!");
			}
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Upload new apk to developer console
			AbstractInputStreamContent apkFile = new FileContent(APK_MIME_TYPE, new File(app.getAppContext().getApkPath()));
			Upload uploadRequest = edits.apks().upload(app.getApplicationId(), editId, apkFile);
			Apk apk = uploadRequest.execute();
			System.out.println(String.format("Version code %d has been uploaded", apk.getVersionCode()));
			
			if (app.getAppContext().getTrackType().equals(TrackType.ROLLOUT)) {
				Track currentRolloutTrack = getTrack(app, edits, editId);
				if (currentRolloutTrack == null || currentRolloutTrack.getReleases().isEmpty()) {
					if (app.getAppContext().getUserFraction() == null) {
						app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
					}
				} else {
					if (app.getAppContext().getUserFraction() == null) {
						app.getAppContext().setUserFraction(currentRolloutTrack.getReleases().get(0).getUserFraction());
					}
				}
			}
			
			// Assign apk to track.
			Track track = new Track();
			track.setTrack(app.getAppContext().getTrackType().getKey());
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setName(app.getAppContext().getReleaseName());
			trackRelease.setVersionCodes(Collections.singletonList(apk.getVersionCode().longValue()));
			
			TrackReleaseStatus trackReleaseStatus;
			if (app.getAppContext().isDraft()) {
				trackReleaseStatus = TrackReleaseStatus.DRAFT;
			} else if (app.getAppContext().getTrackType().equals(TrackType.ROLLOUT)) {
				trackReleaseStatus = TrackReleaseStatus.IN_PROGRESS;
			} else {
				trackReleaseStatus = TrackReleaseStatus.COMPLETED;
			}
			trackRelease.setStatus(trackReleaseStatus.getKey());
			trackRelease.setUserFraction(app.getAppContext().getUserFraction());
			
			List<LocalizedText> releaseNotes = Lists.newArrayList();
			for (LocaleListing each : app.getLocaleListings()) {
				String changelog = app.getChangelog(each, apk.getVersionCode());
				if (StringUtils.isNotBlank(changelog)) {
					LocalizedText releaseNote = new LocalizedText();
					releaseNote.setLanguage(each.getLanguageTag());
					releaseNote.setText(changelog);
					releaseNotes.add(releaseNote);
				}
			}
			trackRelease.setReleaseNotes(releaseNotes);
			track.setReleases(Collections.singletonList(trackRelease));
			
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
		} catch (GoogleJsonResponseException ex) {
			if (!app.getAppContext().failOnApkUpgradeVersionConflict() && ex.getDetails().getCode() == 403 && ex.getDetails().getMessage().equals("APK specifies a version code that has already been used.")) {
				System.out.println("WARNING | apkUpgradeVersionConflict: APK specifies a version code that has already been used.");
			} else {
				throw new UnexpectedException(ex.getDetails().getMessage(), ex);
			}
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while uploading apk and updating recent changes", ex);
		}
	}
	
	public static void publishBundle(App app) {
		try {
			
			if (Strings.isNullOrEmpty(app.getAppContext().getBundlePath())) {
				throw new UnexpectedException("bundlePath cannot be null or empty!");
			}
			
			if (app.getAppContext().getTrackType() == null) {
				throw new UnexpectedException("trackType cannot be null or empty!");
			}
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Upload new bundle to developer console
			AbstractInputStreamContent bundleFile = new FileContent(BUNDLE_MIME_TYPE, new File(app.getAppContext().getBundlePath()));
			Edits.Bundles.Upload uploadRequest = edits.bundles().upload(app.getApplicationId(), editId, bundleFile);
			Bundle bundle = uploadRequest.execute();
			System.out.println(String.format("Version code %d has been uploaded", bundle.getVersionCode()));
			
			if (app.getAppContext().getTrackType().equals(TrackType.ROLLOUT)) {
				Track currentRolloutTrack = getTrack(app, edits, editId);
				if (currentRolloutTrack == null || currentRolloutTrack.getReleases().isEmpty()) {
					if (app.getAppContext().getUserFraction() == null) {
						app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
					}
				} else {
					if (app.getAppContext().getUserFraction() == null) {
						app.getAppContext().setUserFraction(currentRolloutTrack.getReleases().get(0).getUserFraction());
					}
				}
			}
			
			// Assign bundle to track.
			Track track = new Track();
			track.setTrack(app.getAppContext().getTrackType().getKey());
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setName(app.getAppContext().getReleaseName());
			trackRelease.setVersionCodes(Collections.singletonList(bundle.getVersionCode().longValue()));
			
			TrackReleaseStatus trackReleaseStatus;
			if (app.getAppContext().isDraft()) {
				trackReleaseStatus = TrackReleaseStatus.DRAFT;
			} else if (app.getAppContext().getTrackType().equals(TrackType.ROLLOUT)) {
				trackReleaseStatus = TrackReleaseStatus.IN_PROGRESS;
			} else {
				trackReleaseStatus = TrackReleaseStatus.COMPLETED;
			}
			trackRelease.setStatus(trackReleaseStatus.getKey());
			trackRelease.setUserFraction(app.getAppContext().getUserFraction());
			
			List<LocalizedText> releaseNotes = Lists.newArrayList();
			for (LocaleListing each : app.getLocaleListings()) {
				String changelog = app.getChangelog(each, bundle.getVersionCode());
				if (StringUtils.isNotBlank(changelog)) {
					LocalizedText releaseNote = new LocalizedText();
					releaseNote.setLanguage(each.getLanguageTag());
					releaseNote.setText(changelog);
					releaseNotes.add(releaseNote);
				}
			}
			trackRelease.setReleaseNotes(releaseNotes);
			track.setReleases(Collections.singletonList(trackRelease));
			
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while uploading bundle and updating recent changes", ex);
		}
	}
	
	private static Track getTrack(App app, Edits edits, String editId) throws IOException {
		Edits.Tracks.List getTracksRequest = edits.tracks().list(app.getApplicationId(), editId);
		TracksListResponse tracksListResponse = getTracksRequest.execute();
		for (Track track : tracksListResponse.getTracks()) {
			if (track.getTrack().equals(app.getAppContext().getTrackType().getKey())) {
				return track;
			}
		}
		return null;
	}
	
	public static void increaseStagedRollout(App app) {
		try {
			
			if (app.getAppContext().getUserFraction() == null) {
				throw new UnexpectedException("userFraction cannot be null or empty!");
			}
			
			app.getAppContext().setTrackType(TrackType.ROLLOUT);
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			Track track = new Track();
			track.setTrack(TrackType.ROLLOUT.getKey());
			
			Track currentRolloutTrack = getTrack(app, edits, editId);
			if (currentRolloutTrack == null || currentRolloutTrack.getReleases().isEmpty()) {
				throw new UnexpectedException("No current rollout track found");
			}
			
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setStatus(TrackReleaseStatus.IN_PROGRESS.getKey());
			trackRelease.setUserFraction(app.getAppContext().getUserFraction());
			trackRelease.setVersionCodes(currentRolloutTrack.getReleases().get(0).getVersionCodes());
			track.setReleases(Collections.singletonList(trackRelease));
			
			Edits.Tracks.Patch patchTrackRequest = edits.tracks().patch(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = patchTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while increasing the staged rollout", ex);
		}
	}
	
	public static void haltStagedRollout(App app) {
		try {
			
			app.getAppContext().setTrackType(TrackType.ROLLOUT);
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			Track track = new Track();
			track.setTrack(TrackType.ROLLOUT.getKey());
			
			Track currentRolloutTrack = getTrack(app, edits, editId);
			if (currentRolloutTrack == null || currentRolloutTrack.getReleases().isEmpty()) {
				throw new UnexpectedException("No current rollout track found");
			}
			
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setStatus(TrackReleaseStatus.HALTED.getKey());
			trackRelease.setVersionCodes(currentRolloutTrack.getReleases().get(0).getVersionCodes());
			track.setReleases(Collections.singletonList(trackRelease));
			
			Edits.Tracks.Patch patchTrackRequest = edits.tracks().patch(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = patchTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while halting the staged rollout", ex);
		}
	}
	
	public static void resumeStagedRollout(App app) {
		try {
			
			app.getAppContext().setTrackType(TrackType.ROLLOUT);
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			Track track = new Track();
			track.setTrack(TrackType.ROLLOUT.getKey());
			
			Track currentRolloutTrack = getTrack(app, edits, editId);
			if (currentRolloutTrack == null || currentRolloutTrack.getReleases().isEmpty()) {
				throw new UnexpectedException("No current rollout track found");
			}
			
			TrackRelease trackRelease = new TrackRelease();
			trackRelease.setStatus(TrackReleaseStatus.IN_PROGRESS.getKey());
			trackRelease.setVersionCodes(currentRolloutTrack.getReleases().get(0).getVersionCodes());
			track.setReleases(Collections.singletonList(trackRelease));
			
			Edits.Tracks.Patch patchTrackRequest = edits.tracks().patch(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = patchTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while resuming the staged rollout", ex);
		}
	}
	
	public static void promoteFromInternalToAlpha(App app) {
		promote(app, TrackType.INTERNAL, TrackType.ALPHA);
	}
	
	public static void promoteFromInternalToBeta(App app) {
		promote(app, TrackType.INTERNAL, TrackType.BETA);
	}
	
	public static void promoteFromInternalToRollout(App app) {
		if (app.getAppContext().getUserFraction() == null) {
			app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
		}
		promote(app, TrackType.INTERNAL, TrackType.ROLLOUT);
	}
	
	public static void promoteFromInternalToProduction(App app) {
		promote(app, TrackType.INTERNAL, TrackType.PRODUCTION);
	}
	
	public static void promoteFromAlphaToBeta(App app) {
		promote(app, TrackType.ALPHA, TrackType.BETA);
	}
	public static void promoteFromAlphaToRollout(App app) {
		if (app.getAppContext().getUserFraction() == null) {
			app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
		}
		promote(app, TrackType.ALPHA, TrackType.ROLLOUT);
	}
	
	public static void promoteFromAlphaToProduction(App app) {
		promote(app, TrackType.ALPHA, TrackType.PRODUCTION);
	}
	
	public static void promoteFromBetaToRollout(App app) {
		if (app.getAppContext().getUserFraction() == null) {
			app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
		}
		promote(app, TrackType.BETA, TrackType.ROLLOUT);
	}
	
	public static void promoteFromBetaToProduction(App app) {
		promote(app, TrackType.BETA, TrackType.PRODUCTION);
	}
	
	public static void promoteFromRolloutToProduction(App app) {
		promote(app, TrackType.ROLLOUT, TrackType.PRODUCTION);
	}
	
	private static void promote(App app, TrackType fromTrackType, TrackType toTrackType) {
		try {
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Add APKs/bundles to toTrackType track
			Edits.Tracks.Get getTrackRequest = edits.tracks().get(app.getApplicationId(), editId, fromTrackType.getKey());
			Track fromTrack = getTrackRequest.execute();
			
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
					if (toTrackType.equals(TrackType.ROLLOUT)) {
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
			
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, toTrack.getTrack(), toTrack);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while promoting from " + fromTrackType.getKey() + " to " + toTrackType.getKey(), ex);
		}
	}
	
	public static TracksListResponse getTracks(App app) {
		try {
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			Edits.Tracks.List getTracksRequest = edits.tracks().list(app.getApplicationId(), editId);
			return getTracksRequest.execute();
			
		} catch (GoogleJsonResponseException ex) {
			throw new UnexpectedException(ex.getDetails().getMessage(), ex);
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while getting track", ex);
		}
	}
	
	private static void commitEdit(App app, Edits edits, String editId) throws IOException {
		Commit commitRequest = edits.commit(app.getApplicationId(), editId);
		AppEdit appEdit = commitRequest.execute();
		System.out.println(String.format("App edit with id %s has been comitted", appEdit.getId()));
	}
}
