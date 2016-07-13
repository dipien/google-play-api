package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.LocaleListing

public class ContentVerificationTask extends AbstractTask {

	public ContentVerificationTask() {
		description = "Verify that the content to upload to Google Play is valid."
	}

	@Override
	protected void onExecute(App app) {
		for (LocaleListing each : app.getLocaleListings()) {
			app.getTitle(each);
			app.getFullDescription(each);
			app.getShortDescription(each);
			app.getFullDescription(each);
			app.getFeatureGraphic(each);
			app.getPromoGraphic(each);
			app.getHighResolutionIcon(each);
			app.getPhoneScreenshots(each);
			app.getSevenInchScreenshots(each);
			app.getTenInchScreenshots(each);
		}
	}
}
