package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class MetadataVerificationTask : BaseTask() {

    init {
        description = "Verify that the content to upload to Google Play is valid."
    }

    override fun onExecute(app: App) {
        PublishingService().verifyMetadata(app)
    }
}
