package com.arkan.aresto.data.model

data class Cart(
    var id: Int? = null,
    var productId: String? = null,
    var productName: String,
    var productImgUrl: String,
    var productPrice: Double,
    var productQty: Int = 0,
    var productNotes: String? = null
)
