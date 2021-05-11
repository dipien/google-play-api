package com.jdroid.android.googleplay.publisher

import com.jdroid.android.googleplay.publisher.commons.PropertyResolver
import org.gradle.api.Project
import java.io.File

open class GooglePlayPublisherPluginExtension(project: Project) {

    private val propertyResolver: PropertyResolver = PropertyResolver(project)

    var applicationId: String? = propertyResolver.getStringProp("applicationId")
    var privateKeyJsonFilePath: String? = propertyResolver.getStringProp("privateKeyJsonFilePath")
    var metadataPath: String? = propertyResolver.getStringProp("metadataPath", project.projectDir.absolutePath)
    var locales: String? = propertyResolver.getStringProp("locales")
    var apkPath: String? = propertyResolver.getStringProp("apkPath")
    var apkDir: String? = propertyResolver.getStringProp(
        "apkDir", project.projectDir.absolutePath + File.separator + "build" +
            File.separator + "outputs" + File.separator + "apk" + File.separator + "release"
    )
    var bundlePath: String? = propertyResolver.getStringProp("bundlePath")
    var bundleDir: String? = propertyResolver.getStringProp(
        "bundleDir", project.projectDir.absolutePath + File.separator + "build" +
            File.separator + "outputs" + File.separator + "bundle" + File.separator + "release"
    )
    var deobfuscationFilePath: String? = propertyResolver.getStringProp(
        "deobfuscationFilePath", project.projectDir.absolutePath + File.separator + "build" +
            File.separator + "outputs" + File.separator + "mapping" + File.separator + "release" + File.separator + "mapping.txt"
    )
    var isDeobfuscationFileUploadEnabled: Boolean? = propertyResolver.getBooleanProp("deobfuscationFileUploadEnabled", false)
    var track: String? = propertyResolver.getStringProp("track")
    var userPercentage: Double? = propertyResolver.getDoubleProp("userPercentage")
    var draft: Boolean? = propertyResolver.getBooleanProp("draft")
    var releaseName: String? = propertyResolver.getStringProp("releaseName")
    var dryRun: Boolean? = propertyResolver.getBooleanProp("dryRun")
    var releaseNotesRequired: Boolean? = propertyResolver.getBooleanProp("releaseNotesRequired")
    var videoRequired: Boolean? = propertyResolver.getBooleanProp("videoRequired")
    var promoGraphicRequired: Boolean? = propertyResolver.getBooleanProp("promoGraphicRequired")
    var phoneScreenshotsRequired: Boolean? = propertyResolver.getBooleanProp("phoneScreenshotsRequired")
    var sevenInchScreenshotsRequired: Boolean? = propertyResolver.getBooleanProp("sevenInchScreenshotsRequired")
    var tenInchScreenshotsRequired: Boolean? = propertyResolver.getBooleanProp("tenInchScreenshotsRequired")
    var wearScreenshotsRequired: Boolean? = propertyResolver.getBooleanProp("wearScreenshotsRequired")
    var tvScreenshotsRequired: Boolean? = propertyResolver.getBooleanProp("tvScreenshotsRequired")
    var tvBannerRequired: Boolean? = propertyResolver.getBooleanProp("tvBannerRequired")
    var failOnApkUpgradeVersionConflict: Boolean? = propertyResolver.getBooleanProp("failOnApkUpgradeVersionConflict")
    var readTimeout: Int? = propertyResolver.getIntegerProp("readTimeout")
    var connectTimeout: Int? = propertyResolver.getIntegerProp("connectTimeout")
}
