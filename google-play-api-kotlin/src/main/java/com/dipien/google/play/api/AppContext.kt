package com.dipien.google.play.api

class AppContext {

    // The application id of the app
    var applicationId: String? = null

    // Path to the private key json file
    var privateKeyJsonFilePath: String? = null
    var metadataPath: String? = null
    var locales: List<String> = emptyList()
    var apkPath: String? = null
    var apkDir: String? = null
    var bundlePath: String? = null
    var bundleDir: String? = null
    var deobfuscationFilePath: String? = null
    var isDeobfuscationFileUploadEnabled: Boolean = false
    var trackType: TrackType? = null
    var userFraction: Double? = null
    var isDraft: Boolean = false
    var releaseName: String? = null
    var isDryRun: Boolean = false

    // Validations
    var isReleaseNotesRequired: Boolean = false
    var isVideoRequired: Boolean = false
    var isPromoGraphicRequired: Boolean = false
    var isPhoneScreenshotsRequired: Boolean = true
    var isSevenInchScreenshotsRequired: Boolean = false
    var isTenInchScreenshotsRequired: Boolean = false
    var isWearScreenshotsRequired: Boolean = false
    var isTvScreenshotsRequired: Boolean = false
    var isTvBannerRequired: Boolean = false

    var failOnApkUpgradeVersionConflict: Boolean = true
    var readTimeout: Int = 120000
    var connectTimeout: Int = 120000

    var userPercentage: Double?
        get() = if (userFraction != null) userFraction!! * 100 else null
        set(userPercentage) {
            userFraction = if (userPercentage != null) {
                userPercentage / 100
            } else {
                null
            }
        }
}
