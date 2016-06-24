package com.jdroid.android.googleplay.publisher

public class ListApksTask extends AbstractTask {

	public ListApksTask() {
		description = "List all the historical APKs uploaded"
	}

	@Override
	protected void onExecute(AppContext appContext) {
		GooglePlayPublisher.listApks(appContext);
	}
}
