package com.jdroid.android.googleplay.publisher.task;

import com.jdroid.android.googleplay.publisher.App;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher;

public class IncreaseStagedRolloutTask extends BaseTask {
	
	public IncreaseStagedRolloutTask() {
		setDescription("Increase the fraction of users who should get the current staged rollout APK");
	}
	
	@Override
	protected void onExecute(App app) {
		GooglePlayPublisher.increaseStagedRollout(app);
	}
	
}
