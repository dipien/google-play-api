package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

open class ResumeStagedRolloutTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().resumeStagedRollout(app)
    }

    init {
        description = "Resume the current staged rollout"
    }
}
