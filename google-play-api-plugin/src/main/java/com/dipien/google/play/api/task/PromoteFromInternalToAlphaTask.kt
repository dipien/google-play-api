package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class PromoteFromInternalToAlphaTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromInternalToAlpha(app)
    }

    init {
        description = "Promote a current internal to alpha"
    }
}
