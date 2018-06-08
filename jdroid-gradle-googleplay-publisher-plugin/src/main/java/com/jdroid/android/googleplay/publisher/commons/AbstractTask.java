package com.jdroid.android.googleplay.publisher.commons;

import com.jdroid.android.googleplay.publisher.GooglePlayPublisherPlugin;
import com.jdroid.android.googleplay.publisher.GooglePlayPublisherPluginExtension;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class AbstractTask extends DefaultTask {
	
	protected PropertyResolver propertyResolver;
	
	@TaskAction
	public final void doExecute() {
		propertyResolver = new PropertyResolver(getProject());
		onExecute();
	}
	
	protected GooglePlayPublisherPluginExtension getExtension() {
		return (GooglePlayPublisherPluginExtension)getProject().getExtensions().findByName(GooglePlayPublisherPlugin.EXTENSION_NAME);
	}
	
	protected abstract void onExecute();
	
}
