package com.arkan.aresto.data.datasource.product

import com.arkan.aresto.data.source.network.model.checkout.CheckoutRequestPayload
import com.arkan.aresto.data.source.network.model.checkout.CheckoutResponse
import com.arkan.aresto.data.source.network.model.product.ProductResponse
import com.arkan.aresto.data.source.network.services.ArestoApiService

class ProductApiDataSource(
    private val service: ArestoApiService
) : ProductDataSource {
    override suspend fun getProductList(categorySlug: String?): ProductResponse {
        return service.getProducts(categorySlug)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}