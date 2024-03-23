package com.arkan.aresto.data.model

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String
)
