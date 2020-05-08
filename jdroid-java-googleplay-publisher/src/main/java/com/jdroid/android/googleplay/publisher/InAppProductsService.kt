package com.jdroid.android.googleplay.publisher

import com.google.api.services.androidpublisher.model.InAppProduct

class InAppProductsService : AbstractGooglePlayPublisher() {

    fun getInAppProducts(app: App): List<InAppProduct> {
        val listResponse = init(app.appContext).inappproducts().list(app.applicationId).execute()
        return listResponse.inappproduct
    }

    fun getInAppProduct(app: App, sku: String): InAppProduct {
        return init(app.appContext).inappproducts().get(app.applicationId, sku).execute()
    }

    fun updateInAppProduct(app: App, sku: String, inAppProduct: InAppProduct) {
        init(app.appContext).inappproducts().update(app.applicationId, sku, inAppProduct).execute()
    }
}
