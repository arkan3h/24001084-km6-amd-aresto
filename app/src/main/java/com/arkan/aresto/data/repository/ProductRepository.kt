package com.arkan.aresto.data.repository

import com.arkan.aresto.data.datasource.product.ProductDataSource
import com.arkan.aresto.data.mapper.toProducts
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.source.network.model.checkout.CheckoutItemPayload
import com.arkan.aresto.data.source.network.model.checkout.CheckoutRequestPayload
import com.arkan.aresto.utils.ResultWrapper
import com.arkan.aresto.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(categorySlug: String? = null): Flow<ResultWrapper<List<Product>>>

    fun createOrder(products: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class ProductRepositoryImpl(
    private val dataSource: ProductDataSource,
) : ProductRepository {
    override fun getProducts(categorySlug: String?): Flow<ResultWrapper<List<Product>>> {
        return proceedFlow {
            dataSource.getProductList(categorySlug).data.toProducts()
        }
    }

    override fun createOrder(products: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    orders =
                        products.map {
                            CheckoutItemPayload(
                                notes = it.productNotes,
                                name = it.productName,
                                quantity = it.productQty,
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
