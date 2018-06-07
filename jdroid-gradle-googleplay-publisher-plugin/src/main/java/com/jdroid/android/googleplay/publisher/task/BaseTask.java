package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.commons.AbstractTask;
import com.jdroid.android.googleplay.publisher.AppContext;
import com.jdroid.android.googleplay.publisher.TrackType;

public abstract class BaseTask extends AbstractTask {

	@Override
	protected void onExecute() {
		AppContext appContext = new AppContext();
		appContext.setApplicationId(propertyResolver.getStringProp("APPLICATION_ID"));
		appContext.setPrivateKeyJsonFilePath(propertyResolver.getStringProp("PRIVATE_KEY_JSON_FILE_PATH"));
		appContext.setLocales(propertyResolver.getStringListProp("LOCALES"));
		appContext.setReleaseName(propertyResolver.getStringProp("RELEASE_NAME"));
		appContext.setDraft(propertyResolver.getBooleanProp("DRAFT"));

		appContext.setMetadataPath(propertyResolver.getStringProp("METADATA_PATH", getProject().getProjectDir().getAbsolutePath()));

		appContext.setReleaseNotesRequired(propertyResolver.getBooleanProp("RELEASE_NOTES_REQUIRED"));
		appContext.setVideoRequired(propertyResolver.getBooleanProp("VIDEO_REQUIRED"));
		appContext.setPromoGraphicRequired(propertyResolver.getBooleanProp("PROMO_GRAPHIC_REQUIRED"));
		appContext.setPhoneScreenshotsRequired(propertyResolver.getBooleanProp("PHONE_SCREENSHOTS_REQUIRED"));
		appContext.setTvBannerRequired(propertyResolver.getBooleanProp("TV_BANNER_REQUIRED"));
		appContext.setSevenInchScreenshotsRequired(propertyResolver.getBooleanProp("7_INCH_SCREENSHOTS_REQUIRED"));
		appContext.setTenInchScreenshotsRequired(propertyResolver.getBooleanProp("10_INCH_SCREENSHOTS_REQUIRED"));
		appContext.setTvScreenshotsRequired(propertyResolver.getBooleanProp("TV_SCREENSHOTS_REQUIRED"));
		appContext.setWearScreenshotsRequired(propertyResolver.getBooleanProp("WEAR_SCREENSHOTS_REQUIRED"));

		appContext.setApkPath(propertyResolver.getStringProp("APK_PATH"));
		appContext.setBundlePath(propertyResolver.getStringProp("BASE_PATH"));
		appContext.setTrackType(TrackType.findByKey(propertyResolver.getStringProp("TRACK")));
		appContext.setUserFraction(propertyResolver.getDoubleProp("USER_FRACTION"));

		appContext.setFailOnApkUpgradeVersionConflict(propertyResolver.getBooleanProp("FAIL_ON_APK_UPGRADE_VERSION_CONFLICT"));

		onExecute(new App(appContext));
	}

	protected abstract void onExecute(App app);

}
