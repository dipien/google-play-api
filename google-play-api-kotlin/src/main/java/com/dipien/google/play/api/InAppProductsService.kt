package com.dipien.google.play.api

import com.google.api.services.androidpublisher.model.InAppProduct

class InAppProductsService {

    fun getInAppProducts(app: App): List<InAppProduct> {
        val listResponse = GooglePlayHelper.init(app.appContext).inappproducts().list(app.applicationId).execute()
        return listResponse.inappproduct
    }

    fun getInAppProduct(app: App, sku: String): InAppProduct {
        return GooglePlayHelper.init(app.appContext).inappproducts().get(app.applicationId, sku).execute()
    }

    fun updateInAppProduct(app: App, sku: String, inAppProduct: InAppProduct) {
        GooglePlayHelper.init(app.appContext).inappproducts().update(app.applicationId, sku, inAppProduct).execute()
    }
}
