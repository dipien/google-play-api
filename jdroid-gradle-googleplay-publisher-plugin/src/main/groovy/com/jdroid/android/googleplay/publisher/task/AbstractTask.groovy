package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.AppContext
import com.jdroid.android.googleplay.publisher.TrackType
import com.jdroid.java.utils.TypeUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public abstract class AbstractTask extends DefaultTask {

	@TaskAction
	public void doExecute() {
		AppContext appContext = new AppContext();
		appContext.setApplicationId(getStringProp('APPLICATION_ID'));
		appContext.setPrivateKeyJsonFileDirectory(getStringProp('PRIVATE_KEY_JSON_FILE_DIR'));
		appContext.setLocales(getStringProp('LOCALES'));

		appContext.setMetadataPath(getStringProp('METADATA_PATH', project.getProjectDir().getAbsolutePath()));

		appContext.setVideoRequired(getBooleanProp('VIDEO_REQUIRED'));
		appContext.setPromoGraphicRequired(getBooleanProp('PROMO_GRAPHIC_REQUIRED'));
		appContext.setPhoneScreenshotsRequired(getBooleanProp('PHONE_SCREENSHOTS_REQUIRED'));
		appContext.setTvBannerRequired(getBooleanProp('TV_BANNER_REQUIRED'));
		appContext.setSevenInchScreenshotsRequired(getBooleanProp('7_INCH_SCREENSHOTS_REQUIRED'));
		appContext.setTenInchScreenshotsRequired(getBooleanProp('10_INCH_SCREENSHOTS_REQUIRED'));
		appContext.setTvScreenshotsRequired(getBooleanProp('TV_SCREENSHOTS_REQUIRED'));
		appContext.setWearScreenshotsRequired(getBooleanProp('WEAR_SCREENSHOTS_REQUIRED'));

		appContext.setApkPath(getStringProp('APK_PATH'));
		appContext.setTrackType(TrackType.findByKey(getStringProp('TRACK')));
		appContext.setUserFraction(getDoubleProp('USER_FRACTION'));

		appContext.setFailOnApkUpgradeVersionConflict(getBooleanProp('FAIL_ON_APK_UPGRADE_VERSION_CONFLICT'));

		onExecute(new App(appContext));
	}

	protected abstract void onExecute(App app);

	public String getStringProp(String propertyName) {
		return getStringProp(propertyName, null)
	}

	public String getStringProp(String propertyName, String defaultValue) {
		def value = getProp(propertyName)
		if (value == null) {
			return defaultValue
		} else {
			return value.toString();
		}
	}

	public Double getDoubleProp(String propertyName) {
		return TypeUtils.getDouble(getProp(propertyName));
	}

	public Boolean getBooleanProp(String propertyName) {
		return TypeUtils.getBoolean(getProp(propertyName));
	}

	private String getProp(String propertyName) {
		return project.hasProperty(propertyName) ? project.ext.get(propertyName) : System.getenv(propertyName)
	}
}
