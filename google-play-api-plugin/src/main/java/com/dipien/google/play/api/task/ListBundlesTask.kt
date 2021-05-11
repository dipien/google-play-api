package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.GooglePlayPublisher
import org.gradle.api.logging.LogLevel

open class ListBundlesTask : BaseTask() {

    init {
        description = "List all the historical Bundles uploaded"
    }

    override fun onExecute(app: App) {
        GooglePlayPublisher.getBundles(app).forEach { bundle ->
            logger.log(
                LogLevel.LIFECYCLE,
                String.format("Version Code: %d - Binary sha1: %s", bundle.versionCode, bundle.sha1)
            )
        }
    }
}
