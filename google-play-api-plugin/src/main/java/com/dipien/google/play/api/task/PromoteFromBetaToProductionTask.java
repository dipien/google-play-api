package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PromoteFromBetaToProductionTask extends BaseTask {
	
	public PromoteFromBetaToProductionTask() {
		setDescription("Promote a current beta to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromBetaToProduction(app);
	}

}
