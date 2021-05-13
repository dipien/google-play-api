package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PromoteFromInternalToProductionTask extends BaseTask {
	
	public PromoteFromInternalToProductionTask() {
		setDescription("Promote a current internal to production");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().promoteFromInternalToProduction(app);
	}

}
