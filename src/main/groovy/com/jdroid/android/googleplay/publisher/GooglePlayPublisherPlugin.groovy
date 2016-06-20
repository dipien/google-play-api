package com.jdroid.android.googleplay.publisher

import org.gradle.api.Plugin
import org.gradle.api.Project

public class GooglePlayPublisherPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.task('googlePlayPublishListings', type: PublishListingsTask)
		project.task('googlePlayListAPKs', type: ListApksTask)
	}
}
