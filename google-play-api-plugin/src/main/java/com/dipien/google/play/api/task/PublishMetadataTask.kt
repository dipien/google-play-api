package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class PublishMetadataTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().publishMetadata(app)
    }

    init {
        description =
            "Publish metadata (feature/promo graphics, High resolution icon, screenshots, title, short and full descriptions) on Google Play"
    }
}
