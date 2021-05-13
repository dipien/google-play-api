package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PromoteFromAlphaToProductionTask extends BaseTask {
	
	public PromoteFromAlphaToProductionTask() {
		setDescription("Promote a current alpha to production");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().promoteFromAlphaToProduction(app);
	}

}
