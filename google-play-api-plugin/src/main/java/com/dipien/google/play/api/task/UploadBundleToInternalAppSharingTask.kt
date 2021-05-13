package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;


public class UploadBundleToInternalAppSharingTask extends BaseTask {

	public UploadBundleToInternalAppSharingTask() {
		setDescription("Upload Bundle to Internal App Sharing");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().uploadBundleToInternalAppSharing(app);
	}

}
