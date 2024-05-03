package com.arkan.aresto.data.source.network.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<ProductItemResponse>?,
)
