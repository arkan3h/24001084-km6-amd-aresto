package com.arkan.aresto.data.model

import java.util.UUID

data class Product(
    val id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String,
    val price: Double,
    var desc: String,
    var address: String,
    var addressUrl: String
)