package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class IncreaseStagedRolloutTask extends BaseTask {
	
	public IncreaseStagedRolloutTask() {
		setDescription("Increase the fraction of users who should get the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		new PublishingService().increaseStagedRollout(app);
	}
	
}
