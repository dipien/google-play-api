package com.jdroid.android.googleplay.publisher.task

import com.google.api.services.androidpublisher.model.Apk
import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class ListApksTask extends AbstractTask {

	public ListApksTask() {
		description = "List all the historical APKs uploaded"
	}

	@Override
	protected void onExecute(App app) {
		for (Apk apk : GooglePlayPublisher.getApks(app)) {
			System.out.println(String.format("Version Code: %d - Binary sha1: %s", apk.getVersionCode(), apk.getBinary().getSha1()));
		}
	}
}
