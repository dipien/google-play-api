package com.dipien.google.play.api

import com.google.api.services.androidpublisher.model.Review
import com.google.api.services.androidpublisher.model.ReviewsListResponse

class ReviewsService {

    fun getReviews(app: App, maxResults: Long? = null, paginationToken: String? = null, startIndex: Long? = null, translationLanguage: String? = null): ReviewsListResponse {
        val list = GooglePlayHelper.init(app.appContext).reviews().list(app.applicationId)
        list.maxResults = maxResults
        list.token = paginationToken
        list.startIndex = startIndex
        list.translationLanguage = translationLanguage
        return list.execute()
    }

    fun getFilteredReviews(app: App, appVersionCode: Int? = null, maxRating: Int = 5, translationLanguage: String? = null): List<Review> {
        return internalGetFilteredReviews(app, appVersionCode, maxRating, null, null, null, translationLanguage)
    }

    private fun internalGetFilteredReviews(app: App, appVersionCode: Int?, maxRating: Int, maxResults: Long?, paginationToken: String?, startIndex: Long?, translationLanguage: String?): List<Review> {
        val reviews = mutableListOf<Review>()
        val reviewsListResponse = getReviews(app, maxResults, paginationToken, startIndex, translationLanguage)
        reviewsListResponse.reviews.forEach {
            it.comments.forEach { comment ->
                if (comment.userComment != null) {
                    if (appVersionCode == null || comment.userComment.appVersionCode == appVersionCode) {
                        if (comment.userComment.starRating <= maxRating) {
                            if (!reviews.contains(it)) {
                                reviews.add(it)
                            }
                        }
                    }
                }
            }
        }
        // TODO Pagination is not working as expected
        if (reviewsListResponse.pageInfo != null) {
            if (reviewsListResponse.pageInfo.startIndex + reviewsListResponse.pageInfo.resultPerPage < reviewsListResponse.pageInfo.totalResults) {
                reviews.addAll(internalGetFilteredReviews(app, appVersionCode, maxRating, maxResults, reviewsListResponse.tokenPagination.nextPageToken,
                    reviewsListResponse.pageInfo.startIndex.toLong() + reviewsListResponse.pageInfo.resultPerPage, translationLanguage))
            }
        }

        return reviews
    }
}
