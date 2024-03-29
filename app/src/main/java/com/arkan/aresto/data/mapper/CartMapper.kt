package com.arkan.aresto.data.mapper

import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.data.source.local.database.entity.CartEntity

fun Cart?.toCartEntity() = CartEntity(
    id = this?.id,
    productId = this?.productId.orEmpty(),
    productQty = this?.productQty ?: 0,
    productName = this?.productName.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImgUrl = this?.productImgUrl.orEmpty(),
    productNotes = this?.productNotes
)

fun CartEntity?.toCart() = Cart(
    id = this?.id,
    productId = this?.productId.orEmpty(),
    productQty = this?.productQty ?: 0,
    productName = this?.productName.orEmpty(),
    productPrice = this?.productPrice ?: 0.0,
    productImgUrl = this?.productImgUrl.orEmpty(),
    productNotes = this?.productNotes
)

fun List<CartEntity?>.toCartList() = this.map { it.toCart() }