package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PromoteFromInternalToBetaTask extends BaseTask {
	
	public PromoteFromInternalToBetaTask() {
		setDescription("Promote a current internal to beta");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().promoteFromInternalToBeta(app);
	}

}
