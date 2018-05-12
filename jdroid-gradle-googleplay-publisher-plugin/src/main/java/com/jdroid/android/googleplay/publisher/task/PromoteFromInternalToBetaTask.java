package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromInternalToBetaTask extends BaseTask {
	
	public PromoteFromInternalToBetaTask() {
		setDescription("Promote a current internal to beta");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToBeta(app);
	}

}
