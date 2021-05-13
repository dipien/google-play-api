package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PublishBundleTask extends BaseTask {
	
	public PublishBundleTask() {
		setDescription("Publish Bundle to Google Play");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().publishBundle(app);
	}

}
