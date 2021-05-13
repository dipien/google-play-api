package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class PromoteFromInternalToProductionTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromInternalToProduction(app)
    }

    init {
        description = "Promote a current internal to production"
    }
}
