package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class CleanTrackTask extends AbstractTask {

	public CleanTrackTask() {
		description = "Remove all the APKs assigned to a track"
	}

	@Override
	protected void onExecute(App app) {
		 GooglePlayPublisher.cleanTrack(app);
	}
}
