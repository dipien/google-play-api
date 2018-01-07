package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PublishMetadataTask extends BaseTask {
	
	public PublishMetadataTask() {
		setDescription("Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.publishMetadata(app);
	}

}
