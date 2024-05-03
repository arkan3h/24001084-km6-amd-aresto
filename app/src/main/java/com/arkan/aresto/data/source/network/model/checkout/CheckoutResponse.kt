package com.arkan.aresto.data.source.network.model.checkout

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?,
)
