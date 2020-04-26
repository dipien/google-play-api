package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher
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
