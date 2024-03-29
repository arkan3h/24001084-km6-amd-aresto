package com.arkan.aresto.data.datasource.product

import com.arkan.aresto.data.model.Product

interface ProductDataSource {
    fun getProductList() : List<Product>
}