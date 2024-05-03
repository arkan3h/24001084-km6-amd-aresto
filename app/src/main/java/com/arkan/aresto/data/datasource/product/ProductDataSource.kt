package com.arkan.aresto.data.datasource.product

import com.arkan.aresto.data.source.network.model.checkout.CheckoutRequestPayload
import com.arkan.aresto.data.source.network.model.checkout.CheckoutResponse
import com.arkan.aresto.data.source.network.model.product.ProductResponse

interface ProductDataSource {
    suspend fun getProductList(categorySlug: String? = null): ProductResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
