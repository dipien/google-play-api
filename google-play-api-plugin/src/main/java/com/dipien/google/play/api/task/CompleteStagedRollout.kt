package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class CompleteStagedRollout extends BaseTask {
	
	public CompleteStagedRollout() {
		setDescription("Rollout the release to 100% of users");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().completeStagedRollout(app);
	}

}
