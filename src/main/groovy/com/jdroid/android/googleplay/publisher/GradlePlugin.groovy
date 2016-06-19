package com.jdroid.android.googleplay.publisher

import org.gradle.api.Plugin
import org.gradle.api.Project

public class GradlePlugin implements Plugin<Project> {

	public void apply(Project project) {
		super.apply(project);

		project.task('googlePlayPublish', type: GooglePlayPublisherTask)
	}
}
