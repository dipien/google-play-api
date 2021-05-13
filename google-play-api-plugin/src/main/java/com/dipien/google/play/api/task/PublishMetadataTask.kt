package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PublishMetadataTask extends BaseTask {
	
	public PublishMetadataTask() {
		setDescription("Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().publishMetadata(app);
	}

}
