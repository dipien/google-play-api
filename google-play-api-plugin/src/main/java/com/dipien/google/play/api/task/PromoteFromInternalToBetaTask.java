package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.GooglePlayPublisher;

public class PromoteFromInternalToBetaTask extends BaseTask {
	
	public PromoteFromInternalToBetaTask() {
		setDescription("Promote a current internal to beta");
	}

	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.promoteFromInternalToBeta(app);
	}

}
