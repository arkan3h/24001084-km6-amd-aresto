package com.arkan.aresto.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "product_id")
    var productId: String? = null,
    @ColumnInfo(name = "product_name")
    var productName: String,
    @ColumnInfo(name = "product_img_url")
    var productImgUrl: String,
    @ColumnInfo(name = "product_price")
    var productPrice: Double,
    @ColumnInfo(name = "product_quantity")
    var productQty: Int = 0,
    @ColumnInfo(name = "product_notes")
    var productNotes: String? = null,
)
