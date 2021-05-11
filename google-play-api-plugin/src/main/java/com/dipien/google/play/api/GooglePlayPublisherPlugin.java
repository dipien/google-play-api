package com.dipien.google.play.api;

import com.dipien.google.play.api.task.ListTracksTask;
import com.dipien.google.play.api.task.MetadataVerificationTask;
import com.dipien.google.play.api.task.PromoteFromAlphaToBetaTask;
import com.dipien.google.play.api.task.PromoteFromAlphaToProductionTask;
import com.dipien.google.play.api.task.PromoteFromBetaToProductionTask;
import com.dipien.google.play.api.task.PromoteFromInternalToAlphaTask;
import com.dipien.google.play.api.task.PromoteFromInternalToBetaTask;
import com.dipien.google.play.api.task.PromoteFromInternalToProductionTask;
import com.dipien.google.play.api.task.PublishApkTask;
import com.dipien.google.play.api.task.PublishBundleTask;
import com.dipien.google.play.api.task.PublishMetadataTask;
import com.dipien.google.play.api.task.ResumeStagedRolloutTask;
import com.dipien.google.play.api.task.UploadBundleToInternalAppSharingTask;
import com.dipien.google.play.api.task.CompleteStagedRollout;
import com.dipien.google.play.api.task.HaltStagedRolloutTask;
import com.dipien.google.play.api.task.IncreaseStagedRolloutTask;
import com.dipien.google.play.api.task.ListApksTask;
import com.dipien.google.play.api.task.ListBundlesTask;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GooglePlayPublisherPlugin implements Plugin<Project> {
	
	public static final String EXTENSION_NAME = "googlePlay";
	
	@Override
	public void apply(Project project) {
		
		project.getExtensions().create(EXTENSION_NAME, GooglePlayPublisherPluginExtension.class, project);
		
		
		project.getTasks().create("googlePlayVerifyMetadata", MetadataVerificationTask.class);
		project.getTasks().create("googlePlayPublishMetadata", PublishMetadataTask.class);
		project.getTasks().create("googlePlayListAPKs", ListApksTask.class);
		project.getTasks().create("googlePlayListBundles", ListBundlesTask.class);
		project.getTasks().create("googlePlayPublishAPK", PublishApkTask.class);
		project.getTasks().create("googlePlayPublishBundle", PublishBundleTask.class);
		project.getTasks().create("googlePlayUploadBundleToInternalAppSharing", UploadBundleToInternalAppSharingTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToAlpha", PromoteFromInternalToAlphaTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToBeta", PromoteFromInternalToBetaTask.class);
		project.getTasks().create("googlePlayPromoteFromInternalToProduction", PromoteFromInternalToProductionTask.class);
		project.getTasks().create("googlePlayPromoteFromAlphaToBeta", PromoteFromAlphaToBetaTask.class);
		project.getTasks().create("googlePlayPromoteFromAlphaToProduction", PromoteFromAlphaToProductionTask.class);
		project.getTasks().create("googlePlayPromoteFromBetaToProduction", PromoteFromBetaToProductionTask.class);
		project.getTasks().create("googlePlayIncreaseStagedRollout", IncreaseStagedRolloutTask.class);
		project.getTasks().create("googlePlayHaltStagedRollout", HaltStagedRolloutTask.class);
		project.getTasks().create("googlePlayResumeStagedRollout", ResumeStagedRolloutTask.class);
		project.getTasks().create("googlePlayCompleteStagedRollout", CompleteStagedRollout.class);
		project.getTasks().create("googlePlayListTracks", ListTracksTask.class);
	}

}
