package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromBetaToRolloutTask extends BaseTask {
	
	public PromoteFromBetaToRolloutTask() {
		setDescription("Promote a current beta to rollout");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromBetaToRollout(app);
	}

}
