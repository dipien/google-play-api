package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PromoteFromBetaToProductionTask extends BaseTask {
	
	public PromoteFromBetaToProductionTask() {
		setDescription("Promote a current beta to production");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().promoteFromBetaToProduction(app);
	}

}
