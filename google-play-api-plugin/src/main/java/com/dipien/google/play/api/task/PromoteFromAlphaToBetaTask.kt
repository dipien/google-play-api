package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class PromoteFromAlphaToBetaTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().promoteFromAlphaToBeta(app)
    }

    init {
        description = "Promote a current alpha to beta"
    }
}
