package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PublishBundleTask extends BaseTask {
	
	public PublishBundleTask() {
		setDescription("Publish Bundle to Google Play");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.publishBundle(app);
	}

}
