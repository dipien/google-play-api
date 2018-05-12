package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromRolloutToProductionTask extends BaseTask {
	
	public PromoteFromRolloutToProductionTask() {
		setDescription("Promote a current staged rollout to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromRolloutToProduction(app);
	}

}
