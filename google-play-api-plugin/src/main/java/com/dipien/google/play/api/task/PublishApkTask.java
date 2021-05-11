package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PublishApkTask extends BaseTask {
	
	public PublishApkTask() {
		setDescription("Publish APK to Google Play");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.publishApk(app);
	}

}
