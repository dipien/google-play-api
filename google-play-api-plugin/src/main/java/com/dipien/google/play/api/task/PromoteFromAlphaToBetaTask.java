package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PromoteFromAlphaToBetaTask extends BaseTask {
	
	public PromoteFromAlphaToBetaTask() {
		setDescription("Promote a current alpha to beta");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromAlphaToBeta(app);
	}

}
