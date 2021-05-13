package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class ResumeStagedRolloutTask extends BaseTask {
	
	public ResumeStagedRolloutTask() {
		setDescription("Resume the current staged rollout");
	}
	
	@Override
	protected void onExecute(App app) {
		new PublishingService().resumeStagedRollout(app);
	}
	
}
