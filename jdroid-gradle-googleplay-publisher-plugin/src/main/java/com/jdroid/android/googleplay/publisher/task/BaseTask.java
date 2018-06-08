package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.AppContext;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisherPluginExtension;
import com.jdroid.android.googleplay.publisher.TrackType;
import com.jdroid.android.googleplay.publisher.commons.AbstractTask;
import com.jdroid.java.utils.StringUtils;

public abstract class BaseTask extends AbstractTask {

	@Override
	protected void onExecute() {
		
		GooglePlayPublisherPluginExtension extension = getExtension();
		
		AppContext appContext = new AppContext();
		appContext.setApplicationId(extension.getApplicationId());
		appContext.setPrivateKeyJsonFilePath(extension.getPrivateKeyJsonFilePath());
		appContext.setLocales(StringUtils.splitWithCommaSeparator(extension.getLocales()));
		appContext.setReleaseName(extension.getReleaseName());
		appContext.setDraft(extension.getDraft());

		appContext.setMetadataPath(extension.getMetadataPath());

		appContext.setReleaseNotesRequired(extension.getReleaseNotesRequired());
		appContext.setVideoRequired(extension.getVideoRequired());
		appContext.setPromoGraphicRequired(extension.getPromoGraphicRequired());
		appContext.setPhoneScreenshotsRequired(extension.getPhoneScreenshotsRequired());
		appContext.setTvBannerRequired(extension.getTvBannerRequired());
		appContext.setSevenInchScreenshotsRequired(extension.getSevenInchScreenshotsRequired());
		appContext.setTenInchScreenshotsRequired(extension.getTenInchScreenshotsRequired());
		appContext.setTvScreenshotsRequired(extension.getTvScreenshotsRequired());
		appContext.setWearScreenshotsRequired(extension.getWearScreenshotsRequired());

		appContext.setApkPath(extension.getApkPath());
		appContext.setBundlePath(extension.getBundlePath());
		appContext.setTrackType(TrackType.findByKey(extension.getTrack()));
		appContext.setUserFraction(extension.getUserFraction());

		appContext.setFailOnApkUpgradeVersionConflict(extension.getFailOnApkUpgradeVersionConflict());

		onExecute(new App(appContext));
	}

	protected abstract void onExecute(App app);

}
