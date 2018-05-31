package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class ResumeStagedRolloutTask extends BaseTask {
	
	public ResumeStagedRolloutTask() {
		setDescription("Resume the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.resumeStagedRollout(app);
	}
	
}
