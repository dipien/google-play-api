package com.dipien.google.play.api

import com.google.api.client.http.AbstractInputStreamContent
import com.google.api.client.http.FileContent
import com.google.api.client.util.Lists
import java.io.File
import java.lang.RuntimeException
import java.util.Locale

class LocaleListing(private val locale: Locale?, listingPath: String) {

    private val basePath: String = listingPath + File.separator + "googleplay" + File.separator + (if (locale != null) locale.toLanguageTag() else "default") + File.separator

    val languageTag: String
        get() = locale!!.toLanguageTag()

    fun getTitle(defaultLocaleListing: LocaleListing): String? {
        return getDetailsContent("title", true, defaultLocaleListing)
    }

    fun getShortDescription(defaultLocaleListing: LocaleListing): String? {
        return getDetailsContent("short_description", true, defaultLocaleListing)
    }

    fun getFullDescription(defaultLocaleListing: LocaleListing): String? {
        return getDetailsContent("full_description", true, defaultLocaleListing)
    }

    fun getVideo(defaultLocaleListing: LocaleListing, required: Boolean): String? {
        return getDetailsContent("video", required, defaultLocaleListing)
    }

    fun getReleaseNotes(versionCode: Int, defaultLocaleListing: LocaleListing, required: Boolean): String? {
        var releaseNotes = getReleaseNotes(versionCode)
        if (releaseNotes == null) {
            releaseNotes = defaultLocaleListing.getReleaseNotes(versionCode)
        }
        if (releaseNotes == null && required) {
            throw RuntimeException("Release notes for version code $versionCode were not found for locale $languageTag")
        }
        return releaseNotes
    }

    private fun getReleaseNotes(versionCode: Int): String? {
        val file = File(basePath + "release_notes" + File.separator + versionCode + ".txt")
        return if (file.exists()) file.readText() else getDefaultReleaseNotes()
    }

    private fun getDefaultReleaseNotes(): String? {
        val file = File(basePath + "release_notes" + File.separator + "default_release_notes.txt")
        return if (file.exists()) file.readText() else null
    }

    fun getFeatureGraphic(defaultLocaleListing: LocaleListing): AbstractInputStreamContent? {
        return getImageContent("feature_graphic", true, defaultLocaleListing)
    }

    fun getPromoGraphic(defaultLocaleListing: LocaleListing, required: Boolean): AbstractInputStreamContent? {
        return getImageContent("promo_graphic", required, defaultLocaleListing)
    }

    fun getHighResolutionIcon(defaultLocaleListing: LocaleListing): AbstractInputStreamContent? {
        return getImageContent("high_resolution_icon", true, defaultLocaleListing)
    }

    fun getPhoneScreenshots(defaultLocaleListing: LocaleListing, required: Boolean): List<AbstractInputStreamContent> {
        return getScreenshots("phone_screenshots", required, defaultLocaleListing)
    }

    fun getSevenInchScreenshots(defaultLocaleListing: LocaleListing, required: Boolean): List<AbstractInputStreamContent> {
        return getScreenshots("seven_inch_screenshots", required, defaultLocaleListing)
    }

    fun getTvBanner(defaultLocaleListing: LocaleListing, required: Boolean): AbstractInputStreamContent? {
        return getImageContent("tv_banner", required, defaultLocaleListing)
    }

    fun getTenInchScreenshots(defaultLocaleListing: LocaleListing, required: Boolean): List<AbstractInputStreamContent> {
        return getScreenshots("ten_inch_screenshots", required, defaultLocaleListing)
    }

    fun getTvScreenshots(defaultLocaleListing: LocaleListing, required: Boolean): List<AbstractInputStreamContent> {
        return getScreenshots("tv_screenshots", required, defaultLocaleListing)
    }

    fun getWearScreenshots(defaultLocaleListing: LocaleListing, required: Boolean): List<AbstractInputStreamContent> {
        return getScreenshots("wear_screenshots", required, defaultLocaleListing)
    }

    private fun getScreenshots(screenSize: String, required: Boolean, defaultLocaleListing: LocaleListing): List<AbstractInputStreamContent> {
        var abstractInputStreamContents = getScreenshots(screenSize)
        if (abstractInputStreamContents.isEmpty()) {
            abstractInputStreamContents = defaultLocaleListing.getScreenshots(screenSize)
        }
        if (abstractInputStreamContents.isEmpty() && required) {
            throw RuntimeException("images" + File.separator + screenSize + " was not found for locale " + languageTag)
        }
        return abstractInputStreamContents
    }

    private fun getScreenshots(screenSize: String): List<AbstractInputStreamContent> {
        val abstractInputStreamContents: MutableList<AbstractInputStreamContent> = Lists.newArrayList()
        for (i in 1..8) {
            val file = File(basePath + "images" + File.separator + screenSize + File.separator + "screenshot" + i + ".png")
            if (file.exists()) {
                abstractInputStreamContents.add(FileContent("image/png", file))
            }
        }
        return abstractInputStreamContents
    }

    private fun getDetailsContent(item: String, required: Boolean, defaultLocaleListing: LocaleListing): String? {
        var detailsContent = getDetailsContent(item)
        if (detailsContent == null) {
            detailsContent = defaultLocaleListing.getDetailsContent(item)
        }
        if (detailsContent == null && required) {
            throw RuntimeException("$item.txt was not found for locale $languageTag")
        }
        return detailsContent
    }

    private fun getDetailsContent(item: String): String? {
        val file = File("$basePath$item.txt")
        return if (file.exists()) file.readText() else null
    }

    private fun getImageContent(item: String, required: Boolean, defaultLocaleListing: LocaleListing): AbstractInputStreamContent? {
        var imageContent = getImagesContent(item)
        if (imageContent == null) {
            imageContent = defaultLocaleListing.getImagesContent(item)
        }
        if (imageContent == null && required) {
            throw RuntimeException("images/$item.png was not found for locale $languageTag")
        }
        return imageContent
    }

    private fun getImagesContent(item: String): AbstractInputStreamContent? {
        val file = File(basePath + "images" + File.separator + item + ".png")
        return if (file.exists()) FileContent("image/png", file) else null
    }
}
