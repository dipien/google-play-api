package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class CompleteStageRollout extends BaseTask {
	
	public CompleteStageRollout() {
		setDescription("Rollout the release to 100% of users");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.completeStageRollout(app);
	}

}
