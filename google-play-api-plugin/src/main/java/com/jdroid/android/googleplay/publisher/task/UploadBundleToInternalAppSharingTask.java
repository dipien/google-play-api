package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;


public class UploadBundleToInternalAppSharingTask extends BaseTask {

	public UploadBundleToInternalAppSharingTask() {
		setDescription("Upload Bundle to Internal App Sharing");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.uploadBundleToInternalAppSharing(app);
	}

}
