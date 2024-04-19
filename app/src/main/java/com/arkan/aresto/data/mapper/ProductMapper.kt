package com.arkan.aresto.data.mapper

import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.source.network.model.product.ProductItemResponse

fun ProductItemResponse?.toProduct() =
    Product(
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imgUrl = this?.imgUrl.orEmpty(),
        desc = this?.desc.orEmpty(),
        address = this?.address.orEmpty(),
        addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
    )

fun Collection<ProductItemResponse>?.toProducts() = this?.map {
    it.toProduct()
} ?: listOf()