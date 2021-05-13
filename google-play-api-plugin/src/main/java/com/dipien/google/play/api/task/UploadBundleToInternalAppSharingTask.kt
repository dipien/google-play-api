package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.PublishingService

class UploadBundleToInternalAppSharingTask : BaseTask() {
    override fun onExecute(app: App) {
        PublishingService().uploadBundleToInternalAppSharing(app)
    }

    init {
        description = "Upload Bundle to Internal App Sharing"
    }
}
