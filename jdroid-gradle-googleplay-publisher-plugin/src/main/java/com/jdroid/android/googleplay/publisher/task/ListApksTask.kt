package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher
import org.gradle.api.logging.LogLevel

open class ListApksTask : BaseTask() {

    init {
        description = "List all the historical APKs uploaded"
    }

    override fun onExecute(app: App) {
        GooglePlayPublisher.getApks(app).forEach { apk ->
            logger.log(LogLevel.LIFECYCLE, String.format("Version Code: %d - Binary sha1: %s", apk.versionCode, apk.binary.sha1))
        }
    }
}
