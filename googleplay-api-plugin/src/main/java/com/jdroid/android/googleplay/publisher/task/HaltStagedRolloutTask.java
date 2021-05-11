package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class HaltStagedRolloutTask extends BaseTask {
	
	public HaltStagedRolloutTask() {
		setDescription("Halt the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.haltStagedRollout(app);
	}
	
}
