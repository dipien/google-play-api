package com.jdroid.android.googleplay.publisher.task;

import com.google.api.services.androidpublisher.model.Bundle;
import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

import org.gradle.api.logging.LogLevel;

public class ListBundlesTask extends BaseTask {
	
	public ListBundlesTask() {
		setDescription("List all the historical Bundles uploaded");
	}

	@Override
	protected void onExecute(App app) {
		for (Bundle bundle : GooglePlayPublisher.getBundles(app)) {
			getLogger().log(LogLevel.INFO, String.format("Version Code: %d - Binary sha1: %s", bundle.getVersionCode(), bundle.getSha1()));
		}

	}

}
