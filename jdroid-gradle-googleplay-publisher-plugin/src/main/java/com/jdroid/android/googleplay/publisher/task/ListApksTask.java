package com.jdroid.android.googleplay.publisher.task;

import com.google.api.services.androidpublisher.model.Apk;
import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

import org.gradle.api.logging.LogLevel;

public class ListApksTask extends BaseTask {
	
	public ListApksTask() {
		setDescription("List all the historical APKs uploaded");
	}

	@Override
	protected void onExecute(App app) {
		for (Apk apk : GooglePlayPublisher.getApks(app)) {
			getLogger().log(LogLevel.LIFECYCLE, String.format("Version Code: %d - Binary sha1: %s", apk.getVersionCode(), apk.getBinary().getSha1()));
		}

	}

}
