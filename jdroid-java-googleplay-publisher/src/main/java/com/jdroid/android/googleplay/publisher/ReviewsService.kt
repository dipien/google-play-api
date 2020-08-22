package com.jdroid.android.googleplay.publisher

import com.google.api.services.androidpublisher.model.Review

class ReviewsService : AbstractGooglePlayPublisher() {

    fun getReviews(app: App, maxResults: Long? = null, paginationToken: String? = null, startIndex: Long? = null, translationLanguage: String? = null): List<Review> {
        val list = init(app.appContext).reviews().list(app.applicationId)
        list.maxResults = maxResults
        list.token = paginationToken
        list.startIndex = startIndex
        list.translationLanguage = translationLanguage
        return list.execute().reviews
    }
}
