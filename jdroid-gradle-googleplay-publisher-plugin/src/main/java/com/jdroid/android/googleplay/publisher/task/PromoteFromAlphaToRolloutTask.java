package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromAlphaToRolloutTask extends BaseTask {
	
	public PromoteFromAlphaToRolloutTask() {
		setDescription("Promote a current alpha to rollout");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromAlphaToRollout(app);
	}

}
