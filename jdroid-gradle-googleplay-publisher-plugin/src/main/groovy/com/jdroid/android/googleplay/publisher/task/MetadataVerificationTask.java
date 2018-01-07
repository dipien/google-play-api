package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class MetadataVerificationTask extends BaseTask {
	
	public MetadataVerificationTask() {
		setDescription("Verify that the content to upload to Google Play is valid.");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.verifyMetadata(app);
	}

}
