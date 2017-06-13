package com.jdroid.android.googleplay.publisher

import com.jdroid.android.googleplay.publisher.task.CleanTrackTask
import com.jdroid.android.googleplay.publisher.task.IncreaseStagedRolloutTask
import com.jdroid.android.googleplay.publisher.task.ListApksTask
import com.jdroid.android.googleplay.publisher.task.ListTracksTask
import com.jdroid.android.googleplay.publisher.task.MetadataVerificationTask
import com.jdroid.android.googleplay.publisher.task.PromoteFromAlphaToBetaTask
import com.jdroid.android.googleplay.publisher.task.PromoteFromAlphaToRolloutTask
import com.jdroid.android.googleplay.publisher.task.PromoteFromBetaToRolloutTask
import com.jdroid.android.googleplay.publisher.task.PromoteFromRolloutToProductionTask
import com.jdroid.android.googleplay.publisher.task.PublishApksTask
import com.jdroid.android.googleplay.publisher.task.PublishListingsTask
import org.gradle.api.Plugin
import org.gradle.api.Project

public class GooglePlayPublisherPlugin implements Plugin<Project> {

	@Override
	void apply(Project project) {
		project.task('googlePlayPublishListings', type: PublishListingsTask)
		project.task('googlePlayListAPKs', type: ListApksTask)
		project.task('googlePlayVerifyMetadata', type: MetadataVerificationTask)
		project.task('googlePlayPublishAPKs', type: PublishApksTask)
		project.task('googlePlayPromoteFromAlphaToBeta', type: PromoteFromAlphaToBetaTask)
		project.task('googlePlayPromoteFromAlphaToRollout', type: PromoteFromAlphaToRolloutTask)
		project.task('googlePlayPromoteFromBetaToRollout', type: PromoteFromBetaToRolloutTask)
		project.task('googlePlayIncreaseStagedRollout', type: IncreaseStagedRolloutTask)
		project.task('googlePlayPromoteFromRolloutToProduction', type: PromoteFromRolloutToProductionTask)
		project.task('googlePlayListTracks', type: ListTracksTask)
		project.task('googlePlayCleanTrack', type: CleanTrackTask)
	}
}
