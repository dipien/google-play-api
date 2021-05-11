package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class CompleteStagedRollout extends BaseTask {
	
	public CompleteStagedRollout() {
		setDescription("Rollout the release to 100% of users");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.completeStagedRollout(app);
	}

}
