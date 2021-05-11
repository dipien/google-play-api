package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromAlphaToProductionTask extends BaseTask {
	
	public PromoteFromAlphaToProductionTask() {
		setDescription("Promote a current alpha to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromAlphaToProduction(app);
	}

}
