package com.arkan.aresto.data.repository

import com.arkan.aresto.data.datasource.product.ProductDataSource
import com.arkan.aresto.data.model.Product

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ProductRepository {
    fun getProducts(): List<Product>
}

class ProductRepositoryImpl(private val dataSource: ProductDataSource) : ProductRepository {
    override fun getProducts(): List<Product> = dataSource.getProductList()
}