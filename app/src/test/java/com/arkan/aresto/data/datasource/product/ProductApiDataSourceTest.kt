package com.arkan.aresto.data.datasource.product

import com.arkan.aresto.data.source.network.model.checkout.CheckoutRequestPayload
import com.arkan.aresto.data.source.network.model.checkout.CheckoutResponse
import com.arkan.aresto.data.source.network.model.product.ProductResponse
import com.arkan.aresto.data.source.network.services.ArestoApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductApiDataSourceTest {
    @MockK
    lateinit var service: ArestoApiService

    private lateinit var dataSource: ProductDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = ProductApiDataSource(service)
    }

    @Test
    fun getProductList() {
        runTest {
            val mockResponse = mockk<ProductResponse>(relaxed = true)
            coEvery {
                service.getProducts(any())
            } returns mockResponse

            val actualResult = dataSource.getProductList("makanan")
            coVerify {
                service.getProducts(any())
            }
            assertEquals(actualResult, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockRequest = mockk<CheckoutRequestPayload>()
            val mockResponse = mockk<CheckoutResponse>(relaxed = true)
            coEvery {
                service.createOrder(any())
            } returns mockResponse

            val actualResult = dataSource.createOrder(mockRequest)
            coVerify {
                service.createOrder(any())
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
