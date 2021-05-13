package com.dipien.google.play.api

import com.google.api.client.http.AbstractInputStreamContent
import java.util.Locale

class App {

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

    val localeListings: MutableList<LocaleListing> by lazy {
        val list = mutableListOf<LocaleListing>()
        for (each in locales) {
            val split = each.split("-")
            val language = split[0]
            var country = ""
            if (split.size > 1) {
                country = split[1]
            }
            list.add(LocaleListing(Locale(language, country), metadataPath!!))
        }
        list
    }

    private val defaultLocaleListing: LocaleListing by lazy { LocaleListing(null, metadataPath!!) }

    fun getTitle(localeListing: LocaleListing): String? {
        return localeListing.getTitle(defaultLocaleListing)
    }

    fun getFullDescription(localeListing: LocaleListing): String? {
        return localeListing.getFullDescription(defaultLocaleListing)
    }

    fun getShortDescription(localeListing: LocaleListing): String? {
        return localeListing.getShortDescription(defaultLocaleListing)
    }

    fun getVideo(localeListing: LocaleListing): String? {
        return localeListing.getVideo(defaultLocaleListing, isVideoRequired)
    }

    fun getFeatureGraphic(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getFeatureGraphic(defaultLocaleListing)
    }

    fun getPromoGraphic(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getPromoGraphic(defaultLocaleListing, isPromoGraphicRequired)
    }

    fun getHighResolutionIcon(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getHighResolutionIcon(defaultLocaleListing)
    }

    fun getTvBanner(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getTvBanner(defaultLocaleListing, isTvBannerRequired)
    }

    fun getPhoneScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getPhoneScreenshots(defaultLocaleListing, isPhoneScreenshotsRequired)
    }

    fun getSevenInchScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getSevenInchScreenshots(defaultLocaleListing, isSevenInchScreenshotsRequired)
    }

    fun getTenInchScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getTenInchScreenshots(defaultLocaleListing, isTenInchScreenshotsRequired)
    }

    fun getWearScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getWearScreenshots(defaultLocaleListing, isWearScreenshotsRequired)
    }

    fun getTvScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getTvScreenshots(defaultLocaleListing, isTvScreenshotsRequired)
    }

    fun getReleaseNotes(localeListing: LocaleListing, versionCode: Int): String? {
        return localeListing.getReleaseNotes(versionCode, defaultLocaleListing, isReleaseNotesRequired)
    }
}
