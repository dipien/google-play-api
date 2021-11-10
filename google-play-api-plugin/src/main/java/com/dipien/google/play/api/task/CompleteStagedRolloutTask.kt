package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class CompleteStagedRolloutTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().completeStagedRollout(app)
    }

    init {
        description = "Rollout the release to 100% of users"
    }
}
