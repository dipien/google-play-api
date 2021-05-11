package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class ResumeStagedRolloutTask extends BaseTask {
	
	public ResumeStagedRolloutTask() {
		setDescription("Resume the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.resumeStagedRollout(app);
	}
	
}
