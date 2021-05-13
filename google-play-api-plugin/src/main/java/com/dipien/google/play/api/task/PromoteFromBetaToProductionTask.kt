package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class PromoteFromBetaToProductionTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromBetaToProduction(app)
    }

    init {
        description = "Promote a current beta to production"
    }
}
