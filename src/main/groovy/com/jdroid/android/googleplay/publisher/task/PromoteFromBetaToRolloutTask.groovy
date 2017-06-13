package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

public class PromoteFromBetaToRolloutTask extends AbstractTask {

	public PromoteFromBetaToRolloutTask() {
		description = "Promote a current beta to rollout"
	}

	@Override
	protected void onExecute(App app) {
		 GooglePlayPublisher.promoteFromBetaToRollout(app);
	}
}
