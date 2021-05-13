package com.dipien.google.play.api.task;

import com.dipien.google.play.api.App;
import com.dipien.google.play.api.PublishingService;

public class PromoteFromInternalToAlphaTask extends BaseTask {
	
	public PromoteFromInternalToAlphaTask() {
		setDescription("Promote a current internal to alpha");
	}

	@Override
	protected void onExecute(App app) {
		new PublishingService().promoteFromInternalToAlpha(app);
	}

}
