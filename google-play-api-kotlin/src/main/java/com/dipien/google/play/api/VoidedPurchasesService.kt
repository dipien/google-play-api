package com.dipien.google.play.api

import com.google.api.services.androidpublisher.model.VoidedPurchasesListResponse

class VoidedPurchasesService {

    fun getVoidedPurchases(app: App, maxResults: Long? = null, paginationToken: String? = null, startIndex: Long? = null, startTime: Long? = null, endTime: Long? = null, type: Int? = null): VoidedPurchasesListResponse {
        val list = GooglePlayHelper.init(app.appContext).purchases().voidedpurchases().list(app.applicationId)
        list.maxResults = maxResults
        list.token = paginationToken
        list.startIndex = startIndex
        list.startTime = startTime
        list.endTime = endTime
        list.type = type
        return list.execute()
    }
}
