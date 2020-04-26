package com.jdroid.android.googleplay.publisher.task

import com.jdroid.android.googleplay.publisher.App
import com.jdroid.android.googleplay.publisher.GooglePlayPublisher

open class MetadataVerificationTask : BaseTask() {

    init {
        description = "Verify that the content to upload to Google Play is valid."
    }

    override fun onExecute(app: App) {
        GooglePlayPublisher.verifyMetadata(app)
    }
}
