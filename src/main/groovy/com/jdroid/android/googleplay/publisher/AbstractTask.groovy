package com.jdroid.android.googleplay.publisher;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class AbstractTask extends DefaultTask {

	@TaskAction
	public void doExecute() {

		AppContext appContext = new AppContext();
		appContext.setApplicationId(getProp('APPLICATION_ID'));
		appContext.setServiceAccountEmail(getProp('SERVICE_ACCOUNT_EMAIL'));
		appContext.setPrivateKeyFile(getProp('PRIVATE_KEY_FILE'));
		appContext.setListingPath(getProp('LISTING_PATH'));
		appContext.setLocales(getProp('LOCALES'));
		appContext.setApkPath(getProp('APK_PATH'));
		appContext.setTrackType(TrackType.findByKey(getProp('TRACK_TYPE')));

		onExecute(appContext);
	}

	public def getProp(String propertyName) {
		return project.hasProperty(propertyName) ? project.ext.get(propertyName) : System.getenv(propertyName)
	}

	protected abstract void onExecute(AppContext appContext);
}
