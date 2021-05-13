package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class IncreaseStagedRolloutTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().increaseStagedRollout(app)
    }

    init {
        description = "Increase the fraction of users who should get the current staged rollout"
    }
}
