package com.jdroid.android.googleplay.publisher;

import com.jdroid.android.googleplay.publisher.task.HaltStagedRolloutTask;
import com.jdroid.android.googleplay.publisher.task.IncreaseStagedRolloutTask;
import com.jdroid.android.googleplay.publisher.task.ListApksTask;
import com.jdroid.android.googleplay.publisher.task.ListBundlesTask;
import com.jdroid.android.googleplay.publisher.task.ListTracksTask;
import com.jdroid.android.googleplay.publisher.task.MetadataVerificationTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromAlphaToBetaTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromAlphaToProductionTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromAlphaToRolloutTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromBetaToProductionTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromBetaToRolloutTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromInternalToAlphaTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromInternalToBetaTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromInternalToProductionTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromInternalToRolloutTask;
import com.jdroid.android.googleplay.publisher.task.PromoteFromRolloutToProductionTask;
import com.jdroid.android.googleplay.publisher.task.PublishApkTask;
import com.jdroid.android.googleplay.publisher.task.PublishBundleTask;
import com.jdroid.android.googleplay.publisher.task.PublishMetadataTask;
import com.jdroid.android.googleplay.publisher.task.ResumeStagedRolloutTask;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GooglePlayPublisherPlugin implements Plugin<Project> {
	
	@Override
	public void apply(Project project) {

		project.getTasks().create("googlePlayVerifyMetadata", MetadataVerificationTask.class);
		project.getTasks().create("googlePlayPublishMetadata", PublishMetadataTask.class);
		project.getTasks().create("googlePlayListAPKs", ListApksTask.class);
		project.getTasks().create("googlePlayListBundles", ListBundlesTask.class);
		project.getTasks().create("googlePlayPublishAPK", PublishApkTask.class);
		project.getTasks().create("googlePlayPublishBundle", PublishBundleTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToAlpha", PromoteFromInternalToAlphaTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToBeta", PromoteFromInternalToBetaTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToRollout", PromoteFromInternalToRolloutTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToProduction", PromoteFromInternalToProductionTask.class);
		project.getTasks().create("googlePlayPromoteFromAlphaToBeta", PromoteFromAlphaToBetaTask.class);
		project.getTasks().create("googlePlayPromoteFromAlphaToRollout", PromoteFromAlphaToRolloutTask.class);
		project.getTasks().create("googlePlayPromoteFromAlphaToProduction", PromoteFromAlphaToProductionTask.class);
		project.getTasks().create("googlePlayPromoteFromBetaToRollout", PromoteFromBetaToRolloutTask.class);
		project.getTasks().create("googlePlayPromoteFromBetaToProduction", PromoteFromBetaToProductionTask.class);
		project.getTasks().create("googlePlayIncreaseStagedRollout", IncreaseStagedRolloutTask.class);
		project.getTasks().create("googlePlayHaltStagedRollout", HaltStagedRolloutTask.class);
		project.getTasks().create("googlePlayResumeStagedRollout", ResumeStagedRolloutTask.class);
		project.getTasks().create("googlePlayPromoteFromRolloutToProduction", PromoteFromRolloutToProductionTask.class);
		project.getTasks().create("googlePlayListTracks", ListTracksTask.class);
	}

}
