package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromInternalToRolloutTask extends BaseTask {
	
	public PromoteFromInternalToRolloutTask() {
		setDescription("Promote a current internal to rollout");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToRollout(app);
	}

}
