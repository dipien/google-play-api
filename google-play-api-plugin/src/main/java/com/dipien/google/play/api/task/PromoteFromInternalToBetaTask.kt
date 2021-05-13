package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class PromoteFromInternalToBetaTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromInternalToBeta(app)
    }

    init {
        description = "Promote a current internal to beta"
    }
}
