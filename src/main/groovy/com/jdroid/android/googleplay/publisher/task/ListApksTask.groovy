package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class ListApksTask extends AbstractTask {

	public ListApksTask() {
		description = "List all the historical APKs uploaded"
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.listApks(app);
	}
}
