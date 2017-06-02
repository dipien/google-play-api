package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class PublishListingsTask extends AbstractTask {

	public PublishListingsTask() {
		description = "Publish listings (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play"
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.updateListings(app);
	}
}
