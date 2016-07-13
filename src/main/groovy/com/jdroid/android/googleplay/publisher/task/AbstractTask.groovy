package com.jdroid.android.googleplay.publisher.task

import com.google.api.client.util.Lists
import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.AppContext
import com.jdroid.android.googleplay.publisher.LocaleListing
import com.jdroid.android.googleplay.publisher.TrackType
import com.jdroid.java.utils.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public abstract class AbstractTask extends DefaultTask {

	@TaskAction
	public void doExecute() {

		AppContext appContext = new AppContext();
		appContext.setApplicationId(getProp('APPLICATION_ID'));
		appContext.setServiceAccountEmail(getProp('SERVICE_ACCOUNT_EMAIL'));
		appContext.setPrivateKeyFile(getProp('PRIVATE_KEY_FILE'));
		appContext.setListingPath(getStringProp('LISTING_PATH', project.rootDir.getAbsolutePath() + File.separator + project.name ));
		appContext.setLocales(getProp('LOCALES'));
		appContext.setApkPath(getProp('APK_PATH'));
		appContext.setTrackType(TrackType.findByKey(getProp('TRACK_TYPE')));
		appContext.setPromoGraphicRequired(getBooleanProp('PROMO_GRAPHIC_REQUIRED', true));
		appContext.setPhoneScreenshotsRequired(getBooleanProp('PHONE_SCREENSHOTS_REQUIRED', true));
		appContext.setSevenInchScreenshotsRequired(getBooleanProp('7_INCH_SCREENSHOTS_REQUIRED', false));
		appContext.setTenInchScreenshotsRequired(getBooleanProp('10_INCH_SCREENSHOTS_REQUIRED', false));

		App app = new App(appContext, getLocaleListings(appContext), getDefaultLocaleListing(appContext))

		onExecute(app);
	}

	protected abstract void onExecute(App app);

	private List<LocaleListing> getLocaleListings(AppContext appContext) {
		List<LocaleListing> localeListings = Lists.newArrayList();
		for (String each : StringUtils.splitToCollectionWithCommaSeparator(appContext.getLocales())) {
			String[] split = each.split("-");
			String language = split[0];
			String country = "";
			if (split.length > 1) {
				country = split[1];
			}
			localeListings.add(new LocaleListing(new Locale(language, country), appContext.getListingPath()));
		}
		return localeListings
	}

	private LocaleListing getDefaultLocaleListing(AppContext appContext) {
		return new LocaleListing(null, appContext.getListingPath())
	}

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

	public Boolean getBooleanProp(String propertyName, Boolean defaultValue) {
		def value = getProp(propertyName)
		if (value == null) {
			return defaultValue
		} else if (value.toString() == 'true') {
			return true
		} else if (value.toString() == 'false') {
			return false
		} else {
			throw new RuntimeException("Invalid Boolean value: " + value)
		}
	}
}
