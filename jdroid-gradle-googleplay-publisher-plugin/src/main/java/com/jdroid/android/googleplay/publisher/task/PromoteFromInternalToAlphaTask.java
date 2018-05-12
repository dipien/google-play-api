package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class PromoteFromInternalToAlphaTask extends BaseTask {
	
	public PromoteFromInternalToAlphaTask() {
		setDescription("Promote a current internal to alpha");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToAlpha(app);
	}

}
