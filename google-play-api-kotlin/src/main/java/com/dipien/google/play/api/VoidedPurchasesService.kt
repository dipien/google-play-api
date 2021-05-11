package com.dipien.google.play.api

import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse

class VoidedPurchasesService : AbstractGooglePlayPublisher() {

    fun getVoidedPurchases(app: App, maxResults: Long? = null, paginationToken: String? = null, startIndex: Long? = null, startTime: Long? = null, endTime: Long? = null, type: Int? = null): VoidedPurchasesListResponse {
        val list = init(app.appContext).purchases().voidedpurchases().list(app.applicationId)
        list.maxResults = maxResults
        list.token = paginationToken
        list.startIndex = startIndex
        list.startTime = startTime
        list.endTime = endTime
        list.type = type
        return list.execute()
    }
}
