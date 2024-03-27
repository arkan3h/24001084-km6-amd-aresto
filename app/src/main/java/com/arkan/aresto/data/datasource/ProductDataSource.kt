package com.arkan.aresto.data.datasource

import com.arkan.aresto.data.model.Product

interface ProductDataSource {
    fun getProductList() : List<Product>
}