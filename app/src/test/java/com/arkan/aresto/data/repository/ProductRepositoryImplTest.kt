package com.arkan.aresto.data.repository

import app.cash.turbine.test
import com.arkan.aresto.data.datasource.product.ProductDataSource
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.data.source.network.model.checkout.CheckoutResponse
import com.arkan.aresto.data.source.network.model.product.ProductItemResponse
import com.arkan.aresto.data.source.network.model.product.ProductResponse
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {
    @MockK
    lateinit var ds: ProductDataSource

    private lateinit var repo: ProductRepository

    private val product1 =
        ProductItemResponse(
            name = "1",
            imgUrl = "1",
            address = "1",
            desc = "1",
            price = 1.0,
        )
    private val product2 =
        ProductItemResponse(
            name = "2",
            imgUrl = "2",
            address = "2",
            desc = "2",
            price = 2.0,
        )
    private val mockListProduct = listOf(product1, product2)

    private val cart1 =
        Cart(
            id = 1,
            productId = "1",
            productName = "1",
            productImgUrl = "1",
            productPrice = 1.0,
            productQty = 1,
            productNotes = "1",
        )
    private val cart2 =
        Cart(
            id = 2,
            productId = "2",
            productName = "2",
            productImgUrl = "2",
            productPrice = 2.0,
            productQty = 2,
            productNotes = "2",
        )
    private val mockCart = listOf(cart1, cart2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = ProductRepositoryImpl(ds)
    }

    @Test
    fun getProductsLoading() {
        val mockResponse = mockk<ProductResponse>()
        every {
            mockResponse.data
        } returns mockListProduct

        runTest {
            coEvery {
                ds.getProductList()
            } returns mockResponse
            repo.getProducts().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getProductList()
                }
            }
        }
    }

    @Test
    fun getProductsSuccess() {
        val mockResponse = mockk<ProductResponse>()
        every {
            mockResponse.data
        } returns mockListProduct

        runTest {
            coEvery {
                ds.getProductList()
            } returns mockResponse
            repo.getProducts().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getProductList()
                }
            }
        }
    }

    @Test
    fun getProductsError() {
        runTest {
            coEvery {
                ds.getProductList()
            } throws IllegalStateException("Mock Error")
            repo.getProducts().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getProductList()
                }
            }
        }
    }

    @Test
    fun getProductsEmpty() {
        val mockListProduct = listOf<ProductItemResponse>()
        val mockResponse = mockk<ProductResponse>()
        every {
            mockResponse.data
        } returns mockListProduct

        runTest {
            coEvery {
                ds.getProductList()
            } returns mockResponse
            repo.getProducts().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getProductList()
                }
            }
        }
    }

    @Test
    fun createOrderLoading() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)

        runTest {
            coEvery {
                ds.createOrder(any())
            } returns mockResponse
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify {
                    ds.createOrder(any())
                }
            }
        }
    }

    @Test
    fun createOrderSuccess() {
        val mockResponse = mockk<CheckoutResponse>(relaxed = true)

        runTest {
            coEvery {
                ds.createOrder(any())
            } returns mockResponse
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify {
                    ds.createOrder(any())
                }
            }
        }
    }

    @Test
    fun createOrderError() {
        runTest {
            coEvery {
                ds.createOrder(any())
            } throws IllegalStateException("Mock Error")
            repo.createOrder(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify {
                    ds.createOrder(any())
                }
            }
        }
    }
}
