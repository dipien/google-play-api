package com.dipien.google.play.api.task

import com.dipien.google.play.api.App
import com.dipien.google.play.api.AppContext
import com.dipien.google.play.api.TrackType
import com.dipien.google.play.api.commons.AbstractTask

abstract class BaseTask : AbstractTask() {

    override fun onExecute() {
        val extension = getExtension()
        val appContext = AppContext()
        appContext.applicationId = extension.applicationId
        appContext.privateKeyJsonFilePath = extension.privateKeyJsonFilePath
        appContext.connectTimeout = extension.connectTimeout
        appContext.readTimeout = extension.readTimeout
        appContext.locales = extension.locales.orEmpty().split(",")
        appContext.releaseName = extension.releaseName
        appContext.isDraft = extension.draft
        appContext.isDryRun = extension.dryRun
        appContext.metadataPath = extension.metadataPath
        appContext.deobfuscationFilePath = extension.deobfuscationFilePath
        appContext.isDeobfuscationFileUploadEnabled = extension.isDeobfuscationFileUploadEnabled
        appContext.isReleaseNotesRequired = extension.releaseNotesRequired
        appContext.isVideoRequired = extension.videoRequired
        appContext.isPromoGraphicRequired = extension.promoGraphicRequired
        appContext.isPhoneScreenshotsRequired = extension.phoneScreenshotsRequired
        appContext.isTvBannerRequired = extension.tvBannerRequired
        appContext.isSevenInchScreenshotsRequired = extension.sevenInchScreenshotsRequired
        appContext.isTenInchScreenshotsRequired = extension.tenInchScreenshotsRequired
        appContext.isTvScreenshotsRequired = extension.tvScreenshotsRequired
        appContext.isWearScreenshotsRequired = extension.wearScreenshotsRequired
        appContext.apkPath = extension.apkPath
        appContext.apkDir = extension.apkDir
        appContext.bundlePath = extension.bundlePath
        appContext.bundleDir = extension.bundleDir
        appContext.trackType = TrackType.findByKey(extension.track!!)
        appContext.userPercentage = extension.userPercentage
        appContext.failOnApkUpgradeVersionConflict = extension.failOnApkUpgradeVersionConflict
        onExecute(App(appContext))
    }

    protected abstract fun onExecute(app: App)
}
