package com.jdroid.android.googleplay.publisher

import com.google.api.services.androidpublisher.model.VoidedPurchase

class VoidedPurchasesService : AbstractGooglePlayPublisher() {

    fun getVoidedPurchases(app: App): List<VoidedPurchase> {
        val listResponse = init(app.appContext).purchases().voidedpurchases().list(app.applicationId).execute()
        return listResponse.voidedPurchases
    }

}
