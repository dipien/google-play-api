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
		
		appContext.setDryRun(extension.getDryRun());

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
		appContext.setApkDir(extension.getApkDir());
		appContext.setBundlePath(extension.getBundlePath());
		appContext.setBundleDir(extension.getBundleDir());
		
		appContext.setTrackType(TrackType.findByKey(extension.getTrack()));
		appContext.setUserPercentage(extension.getUserPercentage());

		appContext.setFailOnApkUpgradeVersionConflict(extension.getFailOnApkUpgradeVersionConflict());

		onExecute(new App(appContext));
	}

	protected abstract void onExecute(App app);

}
