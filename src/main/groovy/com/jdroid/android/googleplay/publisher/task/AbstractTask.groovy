package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.AppContext
import com.jdroid.android.googleplay.publisher.TrackType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public abstract class AbstractTask extends DefaultTask {

	@TaskAction
	public void doExecute() {
		AppContext appContext = new AppContext();
		appContext.setApplicationId(getProp('APPLICATION_ID'));
		appContext.setPrivateKeyJsonFile(getProp('PRIVATE_KEY_JSON_FILE'));
		appContext.setListingPath(getStringProp('LISTING_PATH', project.getProjectDir().getAbsolutePath()));
		appContext.setLocales(getProp('LOCALES'));
		appContext.setApkPath(getProp('APK_PATH'));
		appContext.setTrackType(TrackType.findByKey(getProp('TRACK_TYPE')));
		appContext.setVideoRequired(getBooleanProp('VIDEO_REQUIRED'));
		appContext.setPromoGraphicRequired(getBooleanProp('PROMO_GRAPHIC_REQUIRED'));
		appContext.setPhoneScreenshotsRequired(getBooleanProp('PHONE_SCREENSHOTS_REQUIRED'));
		appContext.setSevenInchScreenshotsRequired(getBooleanProp('7_INCH_SCREENSHOTS_REQUIRED'));
		appContext.setTenInchScreenshotsRequired(getBooleanProp('10_INCH_SCREENSHOTS_REQUIRED'));
		onExecute(new App(appContext));
	}

	protected abstract void onExecute(App app);

	public String getStringProp(String propertyName, String defaultValue) {
		def value = getProp(propertyName)
		if (value == null) {
			return defaultValue
		} else {
			return value.toString();
		}
	}

	public String getProp(String propertyName) {
		return project.hasProperty(propertyName) ? project.ext.get(propertyName) : System.getenv(propertyName)
	}

	public Boolean getBooleanProp(String propertyName) {
		def value = getProp(propertyName)
		if (value == null) {
			return null
		} else if (value.toString() == 'true') {
			return true
		} else if (value.toString() == 'false') {
			return false
		} else {
			throw new RuntimeException("Invalid Boolean value: " + value)
		}
	}
}
