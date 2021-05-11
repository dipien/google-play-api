package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PromoteFromInternalToAlphaTask extends BaseTask {
	
	public PromoteFromInternalToAlphaTask() {
		setDescription("Promote a current internal to alpha");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToAlpha(app);
	}

}
