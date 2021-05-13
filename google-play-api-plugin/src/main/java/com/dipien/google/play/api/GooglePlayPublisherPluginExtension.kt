package com.dipien.google.play.api

import com.dipien.google.play.api.commons.PropertyResolver
import org.gradle.api.Project
import java.io.File

open class GooglePlayPublisherPluginExtension(project: Project) {

    private val propertyResolver: PropertyResolver = PropertyResolver(project)

    var applicationId: String? = propertyResolver.getStringProp("applicationId")
    var privateKeyJsonFilePath: String? = propertyResolver.getStringProp("privateKeyJsonFilePath")
    var metadataPath: String? = propertyResolver.getStringProp("metadataPath", project.projectDir.absolutePath)
    var locales: String? = propertyResolver.getStringProp("locales")
    var bundlePath: String? = propertyResolver.getStringProp("bundlePath")
    var bundleDir: String? = propertyResolver.getStringProp(
        "bundleDir", project.projectDir.absolutePath + File.separator + "app" + File.separator + "build" +
            File.separator + "outputs" + File.separator + "bundle" + File.separator + "release"
    )
    var deobfuscationFilePath: String? = propertyResolver.getStringProp(
        "deobfuscationFilePath", project.projectDir.absolutePath + File.separator + "app" + File.separator + "build" +
            File.separator + "outputs" + File.separator + "mapping" + File.separator + "release" + File.separator + "mapping.txt"
    )
    var isDeobfuscationFileUploadEnabled: Boolean = propertyResolver.getRequiredBooleanProp("deobfuscationFileUploadEnabled", false)
    var track: String? = propertyResolver.getStringProp("track")
    var userPercentage: Double? = propertyResolver.getDoubleProp("userPercentage")
    var draft: Boolean = propertyResolver.getRequiredBooleanProp("draft", false)
    var releaseName: String? = propertyResolver.getStringProp("releaseName")
    var dryRun: Boolean = propertyResolver.getRequiredBooleanProp("dryRun", false)
    var releaseNotesRequired: Boolean = propertyResolver.getRequiredBooleanProp("releaseNotesRequired", false)
    var videoRequired: Boolean = propertyResolver.getRequiredBooleanProp("videoRequired", false)
    var promoGraphicRequired: Boolean = propertyResolver.getRequiredBooleanProp("promoGraphicRequired", false)
    var phoneScreenshotsRequired: Boolean = propertyResolver.getRequiredBooleanProp("phoneScreenshotsRequired", true)
    var sevenInchScreenshotsRequired: Boolean = propertyResolver.getRequiredBooleanProp("sevenInchScreenshotsRequired", false)
    var tenInchScreenshotsRequired: Boolean = propertyResolver.getRequiredBooleanProp("tenInchScreenshotsRequired", false)
    var wearScreenshotsRequired: Boolean = propertyResolver.getRequiredBooleanProp("wearScreenshotsRequired", false)
    var tvScreenshotsRequired: Boolean = propertyResolver.getRequiredBooleanProp("tvScreenshotsRequired", false)
    var tvBannerRequired: Boolean = propertyResolver.getRequiredBooleanProp("tvBannerRequired", false)
    var failOnApkUpgradeVersionConflict: Boolean = propertyResolver.getRequiredBooleanProp("failOnApkUpgradeVersionConflict", false)
    var readTimeout: Int = propertyResolver.getRequiredIntegerProp("readTimeout", 120000)
    var connectTimeout: Int = propertyResolver.getRequiredIntegerProp("connectTimeout", 120000)
}
