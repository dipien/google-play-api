package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class PublishBundleTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().publishBundle(app)
    }

    init {
        description = "Publish Bundle to Google Play"
    }
}
