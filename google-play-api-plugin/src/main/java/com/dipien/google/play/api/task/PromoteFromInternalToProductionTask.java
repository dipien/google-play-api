package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PromoteFromInternalToProductionTask extends BaseTask {
	
	public PromoteFromInternalToProductionTask() {
		setDescription("Promote a current internal to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToProduction(app);
	}

}
