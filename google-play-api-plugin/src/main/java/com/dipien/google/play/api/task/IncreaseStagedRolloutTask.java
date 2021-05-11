package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class IncreaseStagedRolloutTask extends BaseTask {
	
	public IncreaseStagedRolloutTask() {
		setDescription("Increase the fraction of users who should get the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.increaseStagedRollout(app);
	}
	
}
