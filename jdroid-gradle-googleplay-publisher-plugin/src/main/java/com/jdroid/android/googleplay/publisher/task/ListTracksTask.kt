package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher
import org.gradle.api.logging.LogLevel

open class ListTracksTask : BaseTask() {

    private val logLevel = LogLevel.LIFECYCLE

    init {
        description = "List all the tracks and its releases"
    }

    override fun onExecute(app: App) {

        GooglePlayPublisher.getTracks(app).forEach { track ->
            if (track.releases != null) {
                logger.log(logLevel, "Track: " + track.track)
                logger.log(logLevel, "-------------------------------")
                track.releases.forEach { trackRelease ->
                    logger.log(logLevel, "  Release name: " + trackRelease.name)
                    logger.log(logLevel, "  Version codes: " + trackRelease.versionCodes)
                    logger.log(logLevel, "  User fraction: " + trackRelease.userFraction)
                    logger.log(logLevel, "  Status: " + trackRelease.status)
                    logger.log(logLevel, "  Release Notes: " + trackRelease.releaseNotes)
                    logger.log(logLevel, "")
                }
                logger.log(logLevel, "")
            }
        }
    }
}
