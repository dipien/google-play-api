package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromAlphaToBetaTask extends BaseTask {
	
	public PromoteFromAlphaToBetaTask() {
		setDescription("Promote a current alpha to beta");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromAlphaToBeta(app);
	}

}
