package com.jdroid.android.googleplay.publisher.commons;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class AbstractTask extends DefaultTask {
	
	protected PropertyResolver propertyResolver;
	
	@TaskAction
	public final void doExecute() {
		propertyResolver = new PropertyResolver(getProject());
		onExecute();
	}
	
	protected abstract void onExecute();
	
}
