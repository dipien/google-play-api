package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.TrackType
import com.dipien.google.play.api.commons.AbstractTask

abstract class BaseTask : AbstractTask() {

    override fun onExecute() {
        val extension = getExtension()
        val app = App()
        app.applicationId = extension.applicationId
        app.privateKeyJsonFilePath = extension.privateKeyJsonFilePath
        app.connectTimeout = extension.connectTimeout
        app.readTimeout = extension.readTimeout
        app.locales = extension.locales.orEmpty().split(",")
        app.releaseName = extension.releaseName
        app.isDraft = extension.draft
        app.isDryRun = extension.dryRun
        app.metadataPath = extension.metadataPath
        app.deobfuscationFilePath = extension.deobfuscationFilePath
        app.isDeobfuscationFileUploadEnabled = extension.isDeobfuscationFileUploadEnabled
        app.isReleaseNotesRequired = extension.releaseNotesRequired
        app.isVideoRequired = extension.videoRequired
        app.isPromoGraphicRequired = extension.promoGraphicRequired
        app.isPhoneScreenshotsRequired = extension.phoneScreenshotsRequired
        app.isTvBannerRequired = extension.tvBannerRequired
        app.isSevenInchScreenshotsRequired = extension.sevenInchScreenshotsRequired
        app.isTenInchScreenshotsRequired = extension.tenInchScreenshotsRequired
        app.isTvScreenshotsRequired = extension.tvScreenshotsRequired
        app.isWearScreenshotsRequired = extension.wearScreenshotsRequired
        app.bundlePath = extension.bundlePath
        app.bundleDir = extension.bundleDir
        app.trackType = TrackType.findByKey(extension.track!!)
        app.userPercentage = extension.userPercentage
        app.failOnApkUpgradeVersionConflict = extension.failOnApkUpgradeVersionConflict
        onExecute(app)
    }

    protected abstract fun onExecute(app: App)
}
