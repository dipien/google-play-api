package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class HaltStagedRolloutTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().haltStagedRollout(app)
    }

    init {
        description = "Halt the current staged rollout"
    }
}
