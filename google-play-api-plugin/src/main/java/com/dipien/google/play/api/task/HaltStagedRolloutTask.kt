package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class HaltStagedRolloutTask extends BaseTask {
	
	public HaltStagedRolloutTask() {
		setDescription("Halt the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		new PublishingService().haltStagedRollout(app);
	}
	
}
