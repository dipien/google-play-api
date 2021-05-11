package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromBetaToProductionTask extends BaseTask {
	
	public PromoteFromBetaToProductionTask() {
		setDescription("Promote a current beta to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromBetaToProduction(app);
	}

}
