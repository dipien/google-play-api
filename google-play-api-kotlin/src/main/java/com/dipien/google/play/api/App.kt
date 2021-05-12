package com.dipien.google.play.api

import com.google.api.client.http.AbstractInputStreamContent
import com.google.api.client.util.Lists
import java.util.Locale

class App(val appContext: AppContext) {

    val localeListings: MutableList<LocaleListing>
    private lateinit var defaultLocaleListing: LocaleListing

    init {
        localeListings = Lists.newArrayList()
        if (appContext.metadataPath != null) {
            for (each in appContext.locales) {
                val split = each.split("-")
                val language = split[0]
                var country = ""
                if (split.size > 1) {
                    country = split[1]
                }
                localeListings.add(LocaleListing(Locale(language, country), appContext.metadataPath!!))
            }
            defaultLocaleListing = LocaleListing(null, appContext.metadataPath!!)
        }
    }

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
        return localeListing.getVideo(defaultLocaleListing, appContext.isVideoRequired)
    }

    fun getFeatureGraphic(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getFeatureGraphic(defaultLocaleListing)
    }

    fun getPromoGraphic(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getPromoGraphic(defaultLocaleListing, appContext.isPromoGraphicRequired)
    }

    fun getHighResolutionIcon(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getHighResolutionIcon(defaultLocaleListing)
    }

    fun getTvBanner(localeListing: LocaleListing): AbstractInputStreamContent? {
        return localeListing.getTvBanner(defaultLocaleListing, appContext.isTvBannerRequired)
    }

    fun getPhoneScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getPhoneScreenshots(defaultLocaleListing, appContext.isPhoneScreenshotsRequired)
    }

    fun getSevenInchScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getSevenInchScreenshots(defaultLocaleListing, appContext.isSevenInchScreenshotsRequired)
    }

    fun getTenInchScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getTenInchScreenshots(defaultLocaleListing, appContext.isTenInchScreenshotsRequired)
    }

    fun getWearScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getWearScreenshots(defaultLocaleListing, appContext.isWearScreenshotsRequired)
    }

    fun getTvScreenshots(localeListing: LocaleListing): List<AbstractInputStreamContent> {
        return localeListing.getTvScreenshots(defaultLocaleListing, appContext.isTvScreenshotsRequired)
    }

    fun getReleaseNotes(localeListing: LocaleListing, versionCode: Int): String? {
        return localeListing.getReleaseNotes(versionCode, defaultLocaleListing, appContext.isReleaseNotesRequired)
    }

    val applicationId: String?
        get() = appContext.applicationId
}
