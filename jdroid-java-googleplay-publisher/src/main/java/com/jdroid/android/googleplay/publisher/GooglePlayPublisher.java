package com.jdroid.android.googleplay.publisher;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.Lists;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apklistings;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Apks.Upload;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Commit;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Images;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Images.Deleteall;
import com.google.api.services.androidpublisher.AndroidPublisher.Edits.Insert;
import com.google.api.services.androidpublisher.AndroidPublisherScopes;
import com.google.api.services.androidpublisher.model.Apk;
import com.google.api.services.androidpublisher.model.ApkListing;
import com.google.api.services.androidpublisher.model.ApksListResponse;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.ImagesUploadResponse;
import com.google.api.services.androidpublisher.model.Listing;
import com.google.api.services.androidpublisher.model.Track;
import com.google.api.services.androidpublisher.model.TracksListResponse;
import com.jdroid.java.exception.UnexpectedException;
import com.jdroid.java.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
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

	public static final String MIME_TYPE_APK = "application/vnd.android.package-archive";
	
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	public static final double DEFAULT_USER_FRACTION = 0.005;
	
	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;
	
	/**
	 * Performs all necessary setup steps for running requests against the API.
	 * 
	 * @param appContext
	 * @return the {@Link AndroidPublisher} service
	 * @throws GeneralSecurityException
	 * @throws IOException
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
		} catch (GeneralSecurityException e) {
			throw new UnexpectedException(e);
		} catch (IOException e) {
			throw new UnexpectedException(e);
		}
	}
	
	private static Credential authorizeWithServiceAccount(AppContext appContext) throws GeneralSecurityException,
			IOException {

		if (StringUtils.isEmpty(appContext.getPrivateKeyJsonFileDirectory())) {
			throw new UnexpectedException("The private key json file directory is required");
		}
		
		String privateKeyJsonFile = appContext.getPrivateKeyJsonFileDirectory() + java.io.File.separator + appContext.getApplicationId() + ".json";
		InputStream serviceAccountStream = new FileInputStream(privateKeyJsonFile);
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
			
			return apksResponse.getApks();
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while updating listing", ex);
		}
	}
	
	public static void verifyMetadata(App app) {
		System.out.println(("Verifying the content to upload to Google Play on " + app.getAppContext().getMetadataPath() + "/googleplay"));
		
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
			AbstractInputStreamContent apkFile = new FileContent(MIME_TYPE_APK, new File(app.getAppContext().getApkPath()));
			Upload uploadRequest = edits.apks().upload(app.getApplicationId(), editId, apkFile);
			Apk apk = uploadRequest.execute();
			
			Track track = getTrack(app, edits, editId);
			if (track != null && track.getVersionCodes().contains(apk.getVersionCode())) {
				System.out.println("Skipping APK [" + apk.getVersionCode() + "] upload to track " + track.getTrack() + " because it already exists.");
			} else {
				
				System.out.println(String.format("Version code %d has been uploaded", apk.getVersionCode()));
				
				// Remove any previous alpha or beta
				if (app.getAppContext().getTrackType().equals(TrackType.ALPHA) || app.getAppContext().getTrackType().equals(TrackType.BETA)) {
					if (track != null && !track.getVersionCodes().isEmpty()) {
						Boolean replaceTrack = true;
						for (Integer versionCode : track.getVersionCodes()) {
							if (apk.getVersionCode() <= versionCode) {
								replaceTrack = false;
								break;
							}
						}
						
						if (replaceTrack) {
							Track removeTrack = new Track();
							removeTrack.setTrack(app.getAppContext().getTrackType().getKey());
							Edits.Tracks.Update removeTrackRequest = edits.tracks().update(app.getApplicationId(), editId, track.getTrack(), removeTrack);
							removeTrackRequest.execute();
							System.out.println(String.format("Track %s has been removed.", removeTrack.getTrack()));
						}
					}
				} else if (app.getAppContext().getTrackType().equals(TrackType.ROLLOUT)) {
					if (track == null || track.getVersionCodes().isEmpty()) {
						if (app.getAppContext().getUserFraction() == null) {
							app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
						}
					} else {
						if (app.getAppContext().getUserFraction() == null) {
							app.getAppContext().setUserFraction(track.getUserFraction());
						}
					}
				}
				
				// Assign apk to track.
				List<Integer> apkVersionCodes = new ArrayList<>();
				apkVersionCodes.add(apk.getVersionCode());
				
				track = new Track();
				track.setTrack(app.getAppContext().getTrackType().getKey());
				track.setVersionCodes(apkVersionCodes);
				track.setUserFraction(app.getAppContext().getUserFraction());
				
				Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, track.getTrack(), track);
				Track updatedTrack = updateTrackRequest.execute();
				System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			}
			
			
			for (LocaleListing each : app.getLocaleListings()) {
				String changelog = app.getChangelog(each, apk.getVersionCode());
				if (StringUtils.isNotBlank(changelog)) {
					// Update recent changes field in apk listing.
					ApkListing newApkListing = new ApkListing();
					newApkListing.setLanguage(each.getLanguageTag());
					newApkListing.setRecentChanges(changelog);
					Apklistings.Update updateRecentChangesRequest = edits.apklistings().update(app.getApplicationId(),
							editId, apk.getVersionCode(), each.getLanguageTag(), newApkListing);
					updateRecentChangesRequest.execute();
					System.out.println("Recent changes has been updated.");
				}
			}
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while uploading apk and updating recent changes", ex);
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
	
	public static void cleanTrack(App app) {
		try {
			
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
			
			Track track = getTrack(app, edits, editId);
			
			// Remove any previous alpha or beta
			if (track != null && !track.getVersionCodes().isEmpty()) {
				Track removeTrack = new Track();
				removeTrack.setTrack(app.getAppContext().getTrackType().getKey());
				Edits.Tracks.Update removeTrackRequest = edits.tracks().update(app.getApplicationId(), editId, track.getTrack(), removeTrack);
				removeTrackRequest.execute();
				System.out.println(String.format("Track %s has been removed.", removeTrack.getTrack()));
				
				// Commit changes for edit.
				commitEdit(app, edits, editId);
			}
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while removing APKs from tracks", ex);
		}
	}
	
	public static void increaseStagedRollout(App app) {
		try {
			
			if (app.getAppContext().getUserFraction() == null) {
				throw new UnexpectedException("userFraction cannot be null or empty!");
			}
			
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
			track.setUserFraction(app.getAppContext().getUserFraction());
			
			Edits.Tracks.Patch patchTrackRequest = edits.tracks().patch(app.getApplicationId(), editId, track.getTrack(), track);
			Track updatedTrack = patchTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while increasing the staged rollout", ex);
		}
	}
	
	public static void promoteFromAlphaToBeta(App app) {
		try {
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Add APKs to beta track
			Edits.Tracks.Get getTrackRequest = edits.tracks().get(app.getApplicationId(), editId, TrackType.ALPHA.getKey());
			Track alphaTrack = getTrackRequest.execute();
			
			Track betaTrackToAdd = new Track();
			betaTrackToAdd.setTrack(TrackType.BETA.getKey());
			betaTrackToAdd.setVersionCodes(alphaTrack.getVersionCodes());
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, betaTrackToAdd.getTrack(), betaTrackToAdd);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Remove APKs from alpha track
			Track alphaTrackToRemove = new Track();
			alphaTrackToRemove.setTrack(TrackType.ALPHA.getKey());
			alphaTrackToRemove.setVersionCodes(Lists.newArrayList());
			updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, alphaTrackToRemove.getTrack(), alphaTrackToRemove);
			updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", alphaTrackToRemove.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while promoting from alpha to beta", ex);
		}
	}
	
	public static void promoteFromBetaToRollout(App app) {
		try {
			
			if (app.getAppContext().getUserFraction() == null) {
				app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
			}
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Add APKs to rollout track
			Edits.Tracks.Get getTrackRequest = edits.tracks().get(app.getApplicationId(), editId, TrackType.BETA.getKey());
			Track betaTrack = getTrackRequest.execute();
			
			Track rolloutTrackToAdd = new Track();
			rolloutTrackToAdd.setTrack(TrackType.ROLLOUT.getKey());
			rolloutTrackToAdd.setVersionCodes(betaTrack.getVersionCodes());
			rolloutTrackToAdd.setUserFraction(app.getAppContext().getUserFraction());
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, rolloutTrackToAdd.getTrack(), rolloutTrackToAdd);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Remove APKs from beta track
			Track betaTrackToRemove = new Track();
			betaTrackToRemove.setTrack(TrackType.BETA.getKey());
			betaTrackToRemove.setVersionCodes(Lists.newArrayList());
			updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, betaTrackToRemove.getTrack(), betaTrackToRemove);
			updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", betaTrackToRemove.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while promoting from beta to rollout", ex);
		}
	}
	
	public static void promoteFromAlphaToRollout(App app) {
		try {
			
			if (app.getAppContext().getUserFraction() == null) {
				app.getAppContext().setUserFraction(DEFAULT_USER_FRACTION);
			}
			
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Add APKs to rollout track
			Edits.Tracks.Get getTrackRequest = edits.tracks().get(app.getApplicationId(), editId, TrackType.ALPHA.getKey());
			Track alphaTrack = getTrackRequest.execute();
			
			Track rolloutTrackToAdd = new Track();
			rolloutTrackToAdd.setTrack(TrackType.ROLLOUT.getKey());
			rolloutTrackToAdd.setVersionCodes(alphaTrack.getVersionCodes());
			rolloutTrackToAdd.setUserFraction(app.getAppContext().getUserFraction());
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, rolloutTrackToAdd.getTrack(), rolloutTrackToAdd);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Remove APKs from alpha track
			Track alphaTrackToRemove = new Track();
			alphaTrackToRemove.setTrack(TrackType.ALPHA.getKey());
			alphaTrackToRemove.setVersionCodes(Lists.newArrayList());
			updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, alphaTrackToRemove.getTrack(), alphaTrackToRemove);
			updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", alphaTrackToRemove.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while promoting from alpha to rollout", ex);
		}
	}
	
	public static void promoteFromRolloutToProduction(App app) {
		try {
			// Create the API service.
			AndroidPublisher service = init(app.getAppContext());
			Edits edits = service.edits();
			
			// Create a new edit to make changes.
			Insert editRequest = edits.insert(app.getApplicationId(), null);
			AppEdit edit = editRequest.execute();
			String editId = edit.getId();
			System.out.println(String.format("Created edit with id: %s", editId));
			
			// Add APKs to rollout track
			Edits.Tracks.Get getTrackRequest = edits.tracks().get(app.getApplicationId(), editId, TrackType.ROLLOUT.getKey());
			Track rolloutTrack = getTrackRequest.execute();
			
			Track productionTrackToAdd = new Track();
			productionTrackToAdd.setTrack(TrackType.PRODUCTION.getKey());
			productionTrackToAdd.setVersionCodes(rolloutTrack.getVersionCodes());
			Edits.Tracks.Update updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, productionTrackToAdd.getTrack(), productionTrackToAdd);
			Track updatedTrack = updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", updatedTrack.getTrack()));
			
			// Remove APKs from rollout track
			Track rolloutTrackToRemove = new Track();
			rolloutTrackToRemove.setTrack(TrackType.ROLLOUT.getKey());
			rolloutTrackToRemove.setVersionCodes(Lists.newArrayList());
			updateTrackRequest = edits.tracks().update(app.getApplicationId(), editId, rolloutTrackToRemove.getTrack(), rolloutTrackToRemove);
			updateTrackRequest.execute();
			System.out.println(String.format("Track %s has been updated.", rolloutTrackToRemove.getTrack()));
			
			// Commit changes for edit.
			commitEdit(app, edits, editId);
			
		} catch (IOException ex) {
			throw new UnexpectedException("Exception was thrown while promoting from rollout to production", ex);
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
