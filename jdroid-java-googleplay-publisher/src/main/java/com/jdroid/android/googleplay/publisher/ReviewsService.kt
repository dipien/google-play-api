package com.jdroid.android.googleplay.publisher

import com.google.api.services.androidpublisher.model.Review

class ReviewsService : AbstractGooglePlayPublisher() {

    fun getReviews(app: App): List<Review> {
        val listResponse = init(app.appContext).reviews().list(app.applicationId).execute()
        return listResponse.reviews
    }
}
