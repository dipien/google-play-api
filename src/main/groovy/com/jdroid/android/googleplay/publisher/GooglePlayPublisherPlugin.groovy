package com.jdroid.android.googleplay.publisher

import com.jdroid.android.googleplay.publisher.task.ListApksTask
import com.jdroid.android.googleplay.publisher.task.PublishListingsTask
import com.jdroid.android.googleplay.publisher.task.ContentVerificationTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class GooglePlayPublisherPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.task('googlePlayPublishListings', type: PublishListingsTask)
		project.task('googlePlayListAPKs', type: ListApksTask)
		project.task('googlePlayVerifyContent', type: ContentVerificationTask)
	}
}
