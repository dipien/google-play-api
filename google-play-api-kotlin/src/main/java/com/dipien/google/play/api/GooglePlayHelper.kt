package com.dipien.google.play.api

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.androidpublisher.AndroidPublisher
import com.google.api.services.androidpublisher.AndroidPublisher.Edits
import com.google.api.services.androidpublisher.AndroidPublisherScopes
import com.google.api.services.androidpublisher.model.AppEdit
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.GeneralSecurityException

internal object GooglePlayHelper {

    /** Global instance of the HTTP transport.  */
    private val HTTP_TRANSPORT: HttpTransport by lazy { GoogleNetHttpTransport.newTrustedTransport() }

    /**
     * Performs all necessary setup steps for running requests against the API.
     *
     * @param appContext the [AppContext]
     * @return the [AndroidPublisher] service
     */
    @JvmStatic
    fun init(appContext: AppContext): AndroidPublisher {
        if (appContext.applicationId.isNullOrEmpty()) {
            throw RuntimeException("The application id is required")
        }
        return try {
            // Authorization.
            var credential = authorizeWithServiceAccount(appContext)
            credential = setHttpTimeout(appContext, credential)

            // Set up and return API client.
            val builder = AndroidPublisher.Builder(HTTP_TRANSPORT, JacksonFactory.getDefaultInstance(), credential)
            builder.applicationName = appContext.applicationId
            builder.build()
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    private fun setHttpTimeout(appContext: AppContext, requestInitializer: HttpRequestInitializer): HttpRequestInitializer {
        return HttpRequestInitializer { httpRequest ->
            requestInitializer.initialize(httpRequest)
            httpRequest.connectTimeout = appContext.connectTimeout
            httpRequest.readTimeout = appContext.readTimeout
        }
    }

    private fun authorizeWithServiceAccount(appContext: AppContext): HttpRequestInitializer {
        if (appContext.privateKeyJsonFilePath.isNullOrEmpty()) {
            throw RuntimeException("The private key json file path is required")
        }
        return try {
            val serviceAccountStream: InputStream = FileInputStream(appContext.privateKeyJsonFilePath!!)
            val credential = GoogleCredential.fromStream(serviceAccountStream, HTTP_TRANSPORT, JacksonFactory.getDefaultInstance())
            credential.createScoped(setOf(AndroidPublisherScopes.ANDROIDPUBLISHER))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun createEdit(app: App, edits: Edits): AppEdit {
        return try {
            // Create a new edit to make changes.
            val edit = edits.insert(app.applicationId, null).execute()
            LoggerHelper.log(String.format("Created edit with id: %s", edit.id))
            edit
        } catch (ex: GoogleJsonResponseException) {
            throw RuntimeException(if (ex.details != null) ex.details.message else ex.message, ex)
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    @JvmStatic
    fun commitEdit(app: App, edits: Edits, edit: AppEdit) {
        if (app.appContext.isDryRun) {
            LoggerHelper.log(String.format("Dry run mode enabled. App edit with id %s NOT comitted", edit.id))
        } else {
            try {
                val appEdit = edits.commit(app.applicationId, edit.id).execute()
                LoggerHelper.log(String.format("App edit with id %s has been comitted", appEdit.id))
            } catch (ex: GoogleJsonResponseException) {
                throw RuntimeException(ex.details.message, ex)
            } catch (ex: IOException) {
                throw RuntimeException(ex)
            }
        }
    }
}
