package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class PromoteFromAlphaToProductionTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromAlphaToProduction(app)
    }

    init {
        description = "Promote a current alpha to production"
    }
}
