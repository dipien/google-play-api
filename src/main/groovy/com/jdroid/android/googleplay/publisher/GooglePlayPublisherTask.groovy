package com.jdroid.android.googleplay.publisher

import com.google.api.client.util.Lists
import com.jdroid.java.utils.StringUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

public class GooglePlayPublisherTask extends DefaultTask {

	public GooglePlayPublisherTask() {
		description = "Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play"
	}

	@TaskAction
	public void doExecute() {

		String configFile = project.jdroid.getProp('GOOGLE_PLAY_PUBLISHER_CONFIG_FILE')
		AppContext appContext = new AppContext(configFile);

		GooglePlayPublisher.listApks(appContext);

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
		GooglePlayPublisher.updateListings(appContext, localeListings);
		// GooglePlayPublisher.updateApk(appContext, appContext.getApkPath(), appContext.getTrackType(),
		// localeListings);
	}
}
