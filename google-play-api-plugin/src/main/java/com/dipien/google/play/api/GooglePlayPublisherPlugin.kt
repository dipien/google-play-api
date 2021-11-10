package com.dipien.google.play.api

import com.dipien.google.play.api.task.CompleteStagedRolloutTask
import com.dipien.google.play.api.task.HaltStagedRolloutTask
import com.dipien.google.play.api.task.IncreaseStagedRolloutTask
import com.dipien.google.play.api.task.ListApksTask
import com.dipien.google.play.api.task.ListBundlesTask
import com.dipien.google.play.api.task.ListTracksTask
import com.dipien.google.play.api.task.MetadataVerificationTask
import com.dipien.google.play.api.task.PromoteFromAlphaToBetaTask
import com.dipien.google.play.api.task.PromoteFromAlphaToProductionTask
import com.dipien.google.play.api.task.PromoteFromBetaToProductionTask
import com.dipien.google.play.api.task.PromoteFromInternalToAlphaTask
import com.dipien.google.play.api.task.PromoteFromInternalToBetaTask
import com.dipien.google.play.api.task.PromoteFromInternalToProductionTask
import com.dipien.google.play.api.task.PublishBundleTask
import com.dipien.google.play.api.task.PublishMetadataTask
import com.dipien.google.play.api.task.ResumeStagedRolloutTask
import com.dipien.google.play.api.task.UploadBundleToInternalAppSharingTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class GooglePlayPublisherPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "googlePlay"
    }

    override fun apply(project: Project) {
        project.extensions.create(EXTENSION_NAME, GooglePlayPublisherPluginExtension::class.java, project)
        project.tasks.create("googlePlayVerifyMetadata", MetadataVerificationTask::class.java)
        project.tasks.create("googlePlayPublishMetadata", PublishMetadataTask::class.java)
        project.tasks.create("googlePlayListAPKs", ListApksTask::class.java)
        project.tasks.create("googlePlayListBundles", ListBundlesTask::class.java)
        project.tasks.create("googlePlayPublishBundle", PublishBundleTask::class.java)
        project.tasks.create("googlePlayUploadBundleToInternalAppSharing", UploadBundleToInternalAppSharingTask::class.java)
        project.tasks.create("googlePlayPromoteFromInternalToAlpha", PromoteFromInternalToAlphaTask::class.java)
        project.tasks.create("googlePlayPromoteFromInternalToBeta", PromoteFromInternalToBetaTask::class.java)
        project.tasks.create("googlePlayPromoteFromInternalToProduction", PromoteFromInternalToProductionTask::class.java)
        project.tasks.create("googlePlayPromoteFromAlphaToBeta", PromoteFromAlphaToBetaTask::class.java)
        project.tasks.create("googlePlayPromoteFromAlphaToProduction", PromoteFromAlphaToProductionTask::class.java)
        project.tasks.create("googlePlayPromoteFromBetaToProduction", PromoteFromBetaToProductionTask::class.java)
        project.tasks.create("googlePlayIncreaseStagedRollout", IncreaseStagedRolloutTask::class.java)
        project.tasks.create("googlePlayHaltStagedRollout", HaltStagedRolloutTask::class.java)
        project.tasks.create("googlePlayResumeStagedRollout", ResumeStagedRolloutTask::class.java)
        project.tasks.create("googlePlayCompleteStagedRollout", CompleteStagedRolloutTask::class.java)
        project.tasks.create("googlePlayListTracks", ListTracksTask::class.java)
    }
}
