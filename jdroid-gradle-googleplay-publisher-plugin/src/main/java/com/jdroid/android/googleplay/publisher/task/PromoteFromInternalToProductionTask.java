package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromInternalToProductionTask extends BaseTask {
	
	public PromoteFromInternalToProductionTask() {
		setDescription("Promote a current internal to production");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToProduction(app);
	}

}
