package com.dipien.google.play.api

import com.dipien.google.play.api.GooglePlayHelper.commitEdit
import com.dipien.google.play.api.GooglePlayHelper.createEdit
import com.dipien.google.play.api.GooglePlayHelper.init
import com.dipien.google.play.api.LoggerHelper.log
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.AbstractInputStreamContent
import com.google.api.client.http.FileContent
import com.google.api.client.util.Lists
import com.google.api.services.androidpublisher.AndroidPublisher.Edits
import com.google.api.services.androidpublisher.model.Apk
import com.google.api.services.androidpublisher.model.Bundle
import com.google.api.services.androidpublisher.model.InternalAppSharingArtifact
import com.google.api.services.androidpublisher.model.Listing
import com.google.api.services.androidpublisher.model.LocalizedText
import com.google.api.services.androidpublisher.model.Track
import com.google.api.services.androidpublisher.model.TrackRelease
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Helper class to initialize the publisher APIs client library.
 * <p>
 * Before making any calls to the API through the client library you need to call the
 * {@link GooglePlayHelper#init(AppContext)} method. This will run all precondition checks for for client id and
 * secret setup properly in resources/client_secrets.json and authorize this client against the API.
 * </p>
 */
class PublishingService {

    companion object {
        private const val BUNDLE_MIME_TYPE = "application/octet-stream"
        private const val DEOBFUSCATION_MIME_TYPE = "application/octet-stream"
        private const val DEOBFUSCATION_FILE_TYPE = "proguard"
    }

    /**
     * Retrieve all the apks for a given app.
     *
     * @param app
     */
    fun getApks(app: App): List<Apk> {
        return try {
            val edits = init(app).edits()
            val edit = createEdit(app, edits)

            // Get a list of apks.
            val apksResponse = edits.apks().list(app.applicationId, edit.id).execute()
            if (apksResponse.apks != null) apksResponse.apks else emptyList()
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    /**
     * Retrieve all the bundles for a given app.
     *
     * @param app
     */
    fun getBundles(app: App): List<Bundle> {
        return try {
            val edits = init(app).edits()
            val edit = createEdit(app, edits)

            // Get a list of bundles.
            val bundlesListResponse = edits.bundles().list(app.applicationId, edit.id).execute()
            if (bundlesListResponse.bundles != null) bundlesListResponse.bundles else emptyList()
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun verifyMetadata(app: App) {
        log("Verifying the content to upload to Google Play on " + app.metadataPath)
        for (each in app.localeListings) {
            log("Verifying locale " + each.languageTag)
            app.getTitle(each)
            app.getFullDescription(each)
            app.getShortDescription(each)
            app.getFullDescription(each)
            app.getFeatureGraphic(each)
            app.getPromoGraphic(each)
            app.getVideo(each)
            app.getHighResolutionIcon(each)
            app.getPhoneScreenshots(each)
            app.getSevenInchScreenshots(each)
            app.getTenInchScreenshots(each)
            app.getTvBanner(each)
            app.getTvScreenshots(each)
            app.getWearScreenshots(each)
            app.getReleaseNotes(each, 0)
            log("Successful verification on locale " + each.languageTag)
        }
    }

    fun publishMetadata(app: App) {
        try {
            val edits = init(app).edits()
            val edit = createEdit(app, edits)

            // Update listing for each locale of the application.
            for (each in app.localeListings) {
                val localeString = each.languageTag
                val listing = Listing()
                listing.title = app.getTitle(each)
                listing.fullDescription = app.getFullDescription(each)
                listing.shortDescription = app.getShortDescription(each)
                listing.video = app.getVideo(each)
                val updatedListing = edits.listings().update(app.applicationId, edit.id, localeString, listing).execute()
                log(String.format("Created new $localeString app listing with title: %s", updatedListing.title))

                // Feature Graphic
                val featureGraphic = app.getFeatureGraphic(each)
                var response = edits.images().upload(
                    app.applicationId, edit.id,
                    localeString, ImageType.FEATURE_GRAPHIC.key, featureGraphic
                ).execute()
                log(String.format("Feature graphic %s has been updated.", response.image))

                // Promo Graphic
                val promoGraphic = app.getPromoGraphic(each)
                if (promoGraphic != null) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.PROMO_GRAPHIC.key, promoGraphic
                    ).execute()
                    log(String.format("Promo graphic %s has been updated.", response.image))
                }

                // High Resolution Icon
                val highResolutionIcon = app.getHighResolutionIcon(each)
                response = edits.images().upload(
                    app.applicationId, edit.id,
                    localeString, ImageType.ICON.key, highResolutionIcon
                ).execute()
                log(String.format("High resolution icon %s has been updated.", response.image))

                // High Resolution Icon
                val tvBanner = app.getTvBanner(each)
                if (tvBanner != null) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.TV_BANNER.key, tvBanner
                    ).execute()
                    log(String.format("Tv banner %s has been updated.", response.image))
                }

                // Phone Screenshots
                edits.images().deleteall(app.applicationId, edit.id, localeString, ImageType.PHONE_SCREENSHOTS.key).execute()
                log("Phone screenshots has been deleted.")
                for (content in app.getPhoneScreenshots(each)) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.PHONE_SCREENSHOTS.key, content
                    ).execute()
                    log(String.format("Phone screenshot %s has been updated.", response.image))
                }

                // 7-inch Screenshots
                edits.images().deleteall(app.applicationId, edit.id, localeString, ImageType.SEVEN_INCH_SCREENSHOTS.key).execute()
                log("Seven inch screenshots has been deleted.")
                for (content in app.getSevenInchScreenshots(each)) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.SEVEN_INCH_SCREENSHOTS.key, content
                    ).execute()
                    log(String.format("Seven inch screenshot %s has been updated.", response.image))
                }

                // 10-inch Screenshots
                edits.images().deleteall(app.applicationId, edit.id, localeString, ImageType.TEN_INCH_SCREENSHOTS.key).execute()
                log("Ten inch screenshots has been deleted.")
                for (content in app.getTenInchScreenshots(each)) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.TEN_INCH_SCREENSHOTS.key, content
                    ).execute()
                    log(String.format("Ten inch screenshot %s has been updated.", response.image))
                }

                // Tv Screenshots
                edits.images().deleteall(app.applicationId, edit.id, localeString, ImageType.TV_SCREENSHOTS.key).execute()
                log("Tv screenshots has been deleted.")
                for (content in app.getTvScreenshots(each)) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.TV_SCREENSHOTS.key, content
                    ).execute()
                    log(String.format("Tv screenshot %s has been updated.", response.image))
                }

                // Wear Screenshots
                edits.images().deleteall(app.applicationId, edit.id, localeString, ImageType.WEAR_SCREENSHOTS.key).execute()
                log("Wear screenshots has been deleted.")
                for (content in app.getWearScreenshots(each)) {
                    response = edits.images().upload(
                        app.applicationId, edit.id,
                        localeString, ImageType.WEAR_SCREENSHOTS.key, content
                    ).execute()
                    log(String.format("Wear screenshot %s has been updated.", response.image))
                }
            }
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    private fun setDefaultUserFraction(app: App, edits: Edits, editId: String) {
        if (app.trackType == TrackType.PRODUCTION) {
            if (app.userFraction == null) {
                val currentRolloutTrackRelease = getInProgressRollout(app, edits, editId)
                if (currentRolloutTrackRelease != null) {
                    app.userFraction = currentRolloutTrackRelease.userFraction
                }
            } else {
                if (app.userFraction == 1.0) {
                    app.userFraction = null
                }
            }
        } else {
            app.userFraction = null
        }
    }

    fun publishBundle(app: App, inAppUpdatePriority: Int = 0) {
        try {
            if (app.bundleDir.isNullOrEmpty() && app.bundlePath.isNullOrEmpty()) {
                throw RuntimeException("bundleDir and bundlePath cannot be both null or empty!")
            }
            if (app.trackType == null) {
                throw RuntimeException("trackType cannot be null")
            }
            if (app.isDeobfuscationFileUploadEnabled && app.deobfuscationFilePath == null) {
                throw RuntimeException("deobfuscationFilePath cannot be null")
            }
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            val bundleFile = getBundleFile(app)
            val bundle = edits.bundles().upload(app.applicationId, edit.id, bundleFile).execute()
            log(String.format("Version code %d has been uploaded", bundle.versionCode))
            setDefaultUserFraction(app, edits, edit.id)

            // Assign bundle to track.
            val track = Track()
            track.track = app.trackType!!.key
            val trackRelease = TrackRelease()
            trackRelease.name = app.releaseName
            trackRelease.versionCodes = listOf(bundle.versionCode.toLong())
            val trackReleaseStatus: TrackReleaseStatus = when {
                app.isDraft -> {
                    TrackReleaseStatus.DRAFT
                }
                app.userFraction != null -> {
                    TrackReleaseStatus.IN_PROGRESS
                }
                else -> {
                    TrackReleaseStatus.COMPLETED
                }
            }
            trackRelease.inAppUpdatePriority = inAppUpdatePriority
            trackRelease.status = trackReleaseStatus.key
            trackRelease.userFraction = app.userFraction
            val releaseNotes: MutableList<LocalizedText> = Lists.newArrayList()
            for (each in app.localeListings) {
                val releaseNoteText = app.getReleaseNotes(each, bundle.versionCode)
                if (!releaseNoteText.isNullOrBlank()) {
                    val releaseNote = LocalizedText()
                    releaseNote.language = each.languageTag
                    releaseNote.text = releaseNoteText
                    releaseNotes.add(releaseNote)
                    log("Adding release notes for locale " + each.languageTag)
                }
            }
            trackRelease.releaseNotes = releaseNotes
            track.releases = listOf(trackRelease)
            val updatedTrack = edits.tracks().update(app.applicationId, edit.id, track.track, track).execute()
            log(String.format("Track %s has been updated.", updatedTrack.track))

            // Upload deobfuscation file
            if (app.isDeobfuscationFileUploadEnabled) {
                val deobfuscationFilePath = File(app.deobfuscationFilePath!!)
                if (deobfuscationFilePath.exists()) {
                    val deobfuscationFile: AbstractInputStreamContent = FileContent(DEOBFUSCATION_MIME_TYPE, deobfuscationFilePath)
                    edits.deobfuscationfiles().upload(app.applicationId, edit.id, bundle.versionCode, DEOBFUSCATION_FILE_TYPE, deobfuscationFile)
                    log("Adding deobfuscation file " + app.deobfuscationFilePath)
                } else {
                    throw RuntimeException("$deobfuscationFilePath doesn't exist.")
                }
            }

            // Commit changes for edit.
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    private fun getHaltedRollout(app: App, edits: Edits, editId: String): TrackRelease? {
        val currentRolloutTrack = getTrack(app, TrackType.PRODUCTION, edits, editId)
        if (currentRolloutTrack != null && currentRolloutTrack.releases != null) {
            for (trackRelease in currentRolloutTrack.releases) {
                if (trackRelease.status == TrackReleaseStatus.HALTED.key) {
                    return trackRelease
                }
            }
        }
        return null
    }

    private fun getInProgressRollout(app: App, edits: Edits, editId: String): TrackRelease? {
        val currentRolloutTrack = getTrack(app, TrackType.PRODUCTION, edits, editId)
        if (currentRolloutTrack != null && currentRolloutTrack.releases != null) {
            for (trackRelease in currentRolloutTrack.releases) {
                if (trackRelease.status == TrackReleaseStatus.IN_PROGRESS.key) {
                    return trackRelease
                }
            }
        }
        return null
    }

    private fun getTrack(app: App, trackType: TrackType, edits: Edits, editId: String): Track? {
        return try {
            edits.tracks()[app.applicationId, editId, trackType.key].execute()
        } catch (ex: GoogleJsonResponseException) {
            if (ex.details.code == 404) {
                null
            } else {
                throw RuntimeException(ex.details.message, ex)
            }
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun increaseStagedRollout(app: App) {
        try {
            if (app.userFraction == null) {
                throw RuntimeException("userFraction cannot be null")
            }
            if (app.userFraction == 1.0) {
                completeStagedRollout(app)
            } else {
                app.trackType = TrackType.PRODUCTION
                val edits = init(app).edits()
                val edit = createEdit(app, edits)
                val track = Track()
                track.track = TrackType.PRODUCTION.key
                val currentRolloutRelease = getInProgressRollout(app, edits, edit.id)
                    ?: throw RuntimeException("No in progress staged rollout release found")
                val trackRelease = TrackRelease()
                trackRelease.status = TrackReleaseStatus.IN_PROGRESS.key
                trackRelease.userFraction = app.userFraction
                trackRelease.versionCodes = currentRolloutRelease.versionCodes
                track.releases = listOf(trackRelease)
                val updatedTrack = edits.tracks().patch(app.applicationId, edit.id, track.track, track).execute()
                log(String.format("Track %s has been updated.", updatedTrack.track))

                // Commit changes for edit.
                commitEdit(app, edits, edit)
            }
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun haltStagedRollout(app: App) {
        try {
            app.trackType = TrackType.PRODUCTION
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            val track = Track()
            track.track = TrackType.PRODUCTION.key
            val currentRolloutRelease = getInProgressRollout(app, edits, edit.id)
                ?: throw RuntimeException("No in progress staged rollout release found")
            val trackRelease = TrackRelease()
            trackRelease.status = TrackReleaseStatus.HALTED.key
            trackRelease.versionCodes = currentRolloutRelease.versionCodes
            trackRelease.userFraction = currentRolloutRelease.userFraction
            track.releases = listOf(trackRelease)
            val updatedTrack = edits.tracks().patch(app.applicationId, edit.id, track.track, track).execute()
            log(String.format("Track %s has been updated.", updatedTrack.track))

            // Commit changes for edit.
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun resumeStagedRollout(app: App) {
        try {
            app.trackType = TrackType.PRODUCTION
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            val track = Track()
            track.track = TrackType.PRODUCTION.key
            val currentRolloutRelease = getHaltedRollout(app, edits, edit.id)
                ?: throw RuntimeException("No halted staged rollout release found")
            val trackRelease = TrackRelease()
            trackRelease.status = TrackReleaseStatus.IN_PROGRESS.key
            trackRelease.versionCodes = currentRolloutRelease.versionCodes
            trackRelease.userFraction = currentRolloutRelease.userFraction
            track.releases = listOf(trackRelease)
            val updatedTrack = edits.tracks().patch(app.applicationId, edit.id, track.track, track).execute()
            log(String.format("Track %s has been updated.", updatedTrack.track))

            // Commit changes for edit.
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun promoteFromInternalToAlpha(app: App) {
        promote(app, TrackType.INTERNAL, TrackType.ALPHA)
    }

    fun promoteFromInternalToBeta(app: App) {
        promote(app, TrackType.INTERNAL, TrackType.BETA)
    }

    fun promoteFromInternalToProduction(app: App) {
        promote(app, TrackType.INTERNAL, TrackType.PRODUCTION)
    }

    fun promoteFromAlphaToBeta(app: App) {
        promote(app, TrackType.ALPHA, TrackType.BETA)
    }

    fun promoteFromAlphaToProduction(app: App) {
        promote(app, TrackType.ALPHA, TrackType.PRODUCTION)
    }

    fun promoteFromBetaToProduction(app: App) {
        promote(app, TrackType.BETA, TrackType.PRODUCTION)
    }

    private fun promote(app: App, fromTrackType: TrackType, toTrackType: TrackType) {
        try {
            app.trackType = toTrackType
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            setDefaultUserFraction(app, edits, edit.id)

            // Add APKs/bundles to toTrackType track
            val fromTrack = edits.tracks()[app.applicationId, edit.id, fromTrackType.key].execute()
            val toTrack = Track()
            toTrack.track = toTrackType.key
            val toTrackReleases: MutableList<TrackRelease> = Lists.newArrayList()
            for (fromTrackRelease in fromTrack.releases) {
                if (app.releaseName == null || app.releaseName == fromTrackRelease.name) {
                    val toTrackRelease = TrackRelease()
                    toTrackRelease.name = fromTrackRelease.name
                    toTrackRelease.userFraction = app.userFraction
                    toTrackRelease.versionCodes = fromTrackRelease.versionCodes
                    val trackReleaseStatus: TrackReleaseStatus = if (app.userFraction != null) {
                        TrackReleaseStatus.IN_PROGRESS
                    } else {
                        TrackReleaseStatus.COMPLETED
                    }
                    toTrackRelease.status = trackReleaseStatus.key
                    toTrackRelease.releaseNotes = fromTrackRelease.releaseNotes
                    toTrackReleases.add(toTrackRelease)
                }
            }
            toTrack.releases = toTrackReleases
            val updatedTrack = edits.tracks().update(app.applicationId, edit.id, toTrack.track, toTrack).execute()
            log(String.format("Track %s has been updated.", updatedTrack.track))

            // Commit changes for edit.
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun completeStagedRollout(app: App) {
        try {
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            val currentRolloutRelease = getInProgressRollout(app, edits, edit.id)
                ?: throw RuntimeException("No in progress staged rollout release found")
            currentRolloutRelease.status = TrackReleaseStatus.COMPLETED.key
            currentRolloutRelease.userFraction = null
            val productionTrack = Track()
            productionTrack.track = TrackType.PRODUCTION.key
            productionTrack.releases = listOf(currentRolloutRelease)
            val updatedTrack = edits.tracks().update(app.applicationId, edit.id, productionTrack.track, productionTrack).execute()
            log(String.format("Track %s has been updated.", updatedTrack.track))

            // Commit changes for edit.
            commitEdit(app, edits, edit)
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun getTracks(app: App): List<Track> {
        return try {
            val edits = init(app).edits()
            val edit = createEdit(app, edits)
            edits.tracks().list(app.applicationId, edit.id).execute().tracks
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    fun getTrack(app: App): Track? {
        val edits = init(app).edits()
        val edit = createEdit(app, edits)
        return getTrack(app, app.trackType!!, edits, edit.id)
    }

    fun getInternalTrackRelease(app: App): TrackRelease? {
        app.trackType = TrackType.INTERNAL
        val trackReleases = getTrackReleases(app)
        return if (trackReleases.isEmpty()) null else trackReleases[0]
    }

    fun getAlphaTrackReleases(app: App): List<TrackRelease?>? {
        app.trackType = TrackType.ALPHA
        return getTrackReleases(app)
    }

    fun getBetaTrackRelease(app: App): TrackRelease? {
        app.trackType = TrackType.BETA
        val trackReleases = getTrackReleases(app)
        return if (trackReleases.isEmpty()) null else trackReleases[0]
    }

    fun getCompletedProductionTrackRelease(app: App): TrackRelease? {
        app.trackType = TrackType.PRODUCTION
        for (trackRelease in getTrackReleases(app)) {
            if (trackRelease.status == TrackReleaseStatus.COMPLETED.key) {
                return trackRelease
            }
        }
        return null
    }

    fun getStagedRolloutTrackRelease(app: App): TrackRelease? {
        app.trackType = TrackType.PRODUCTION
        for (trackRelease in getTrackReleases(app)) {
            if (trackRelease.status == TrackReleaseStatus.IN_PROGRESS.key) {
                return trackRelease
            }
        }
        return null
    }

    fun getHaltedProductionTrackRelease(app: App): TrackRelease? {
        app.trackType = TrackType.PRODUCTION
        for (trackRelease in getTrackReleases(app)) {
            if (trackRelease.status == TrackReleaseStatus.HALTED.key) {
                return trackRelease
            }
        }
        return null
    }

    private fun getTrackReleases(app: App): List<TrackRelease> {
        val track = getTrack(app)
        return if (track != null) {
            if (track.releases != null) track.releases else Lists.newArrayList()
        } else {
            emptyList()
        }
    }

    fun uploadBundleToInternalAppSharing(app: App): InternalAppSharingArtifact {
        return try {
            if (app.bundleDir.isNullOrEmpty() && app.bundlePath.isNullOrEmpty()) {
                throw RuntimeException("bundleDir and bundlePath cannot be both null or empty!")
            }
            val bundleFile = getBundleFile(app)
            val internalAppSharingArtifacts = init(app).internalappsharingartifacts()
            val uploadBundle = internalAppSharingArtifacts.uploadbundle(app.applicationId, bundleFile)
            if (app.isDryRun) {
                log("Dry run mode enabled. Bundle not uploaded to internal app sharing")
                InternalAppSharingArtifact()
            } else {
                val internalAppSharingArtifact = uploadBundle.execute()
                log("Bundle uploaded to internal app sharing:")
                log("- Certificate Fingerprint: " + internalAppSharingArtifact.certificateFingerprint)
                log("- Download Url: " + internalAppSharingArtifact.downloadUrl)
                internalAppSharingArtifact
            }
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(ex.details.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    private fun getBundleFile(app: App): AbstractInputStreamContent? {
        // Upload new bundle to developer console
        if (app.bundlePath.isNullOrEmpty()) {
            if (!app.bundleDir.isNullOrEmpty()) {
                val filter = Files.list(Paths.get(app.bundleDir!!))
                    .filter label@{ path ->
                        return@label path.fileName.toString().endsWith(".aab") && !path.fileName.toString()
                            .endsWith("unaligned.aab")
                    }

                val count = filter.count()
                if (count == 1L) {
                    app.bundlePath = filter.findAny().get().toAbsolutePath().toString()
                } else if (count == 0L) {
                    throw RuntimeException("Bundle not found")
                } else {
                    throw RuntimeException("More than one Bundle found at the specified path")
                }
            }
        }
        return FileContent(BUNDLE_MIME_TYPE, File(app.bundlePath!!))
    }
}
