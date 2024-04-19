package com.arkan.aresto.data.source.network.model.product

import com.google.gson.annotations.SerializedName

data class ProductItemResponse(
    @SerializedName("nama")
    val name : String?,
    @SerializedName("image_url")
    val imgUrl : String?,
    @SerializedName("harga")
    val price : Double?,
    @SerializedName("detail")
    val desc : String?,
    @SerializedName("alamat_resto")
    val address : String?
)
