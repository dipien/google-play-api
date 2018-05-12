package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PublishApkTask extends BaseTask {
	
	public PublishApkTask() {
		setDescription("Publish APK to Google Play");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.publishApk(app);
	}

}
