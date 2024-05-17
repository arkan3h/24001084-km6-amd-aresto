package com.arkan.aresto.data.repository

import app.cash.turbine.test
import com.arkan.aresto.data.datasource.cart.CartDataSource
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.model.ProductPrice
import com.arkan.aresto.data.source.local.database.entity.CartEntity
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var ds: CartDataSource

    private lateinit var repo: CartRepository

    private val entity1 =
        CartEntity(
            id = 1,
            productId = "1",
            productName = "1",
            productImgUrl = "1",
            productPrice = 1.0,
            productQty = 1,
            productNotes = "1",
        )
    private val entity2 =
        CartEntity(
            id = 2,
            productId = "2",
            productName = "2",
            productImgUrl = "2",
            productPrice = 2.0,
            productQty = 2,
            productNotes = "2",
        )
    private val mockList = listOf(entity1, entity2)
    private val mockCart =
        Cart(
            id = 1,
            productId = "1",
            productName = "1",
            productImgUrl = "1",
            productPrice = 1.0,
            productQty = 3,
            productNotes = "1",
        )
    private val mockCart1 =
        Cart(
            id = 1,
            productId = "1",
            productName = "1",
            productImgUrl = "1",
            productPrice = 1.0,
            productQty = 1,
            productNotes = "1",
        )
    private val price1 =
        ProductPrice(
            name = "1",
            total = 1.0,
        )
    private val price2 =
        ProductPrice(
            name = "2",
            total = 2.0,
        )
    private val mockProductPrice = listOf(price1, price2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CartRepositoryImpl(ds)
    }

    @Test
    fun getUserCartDataLoading() {
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload?.first?.size)
                assertEquals(null, actualData.payload?.second)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartDataSuccess() {
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(5.0, actualData.payload?.second)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartDataError() {
        every {
            ds.getAllCarts()
        } returns
            flow {
                throw IllegalStateException("Mock Error")
            }

        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartDataEmpty() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(0, actualData.payload?.first?.size)
                assertEquals(0.0, actualData.payload?.second)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutDataLoading() {
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload?.first?.size)
                assertEquals(null, actualData.payload?.second?.size)
                assertEquals(null, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutDataSuccess() {
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(mockProductPrice.size, actualData.payload?.second?.size)
                assertEquals(5.0, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutDataError() {
        every {
            ds.getAllCarts()
        } returns
            flow {
                throw IllegalStateException("Mock Error")
            }

        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutDataEmpty() {
        val mockList = listOf<CartEntity>()
        val mockFlow =
            flow {
                emit(mockList)
            }
        every {
            ds.getAllCarts()
        } returns mockFlow

        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(0, actualData.payload?.first?.size)
                assertEquals(0, actualData.payload?.second?.size)
                assertEquals(0.0, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun createCartLoading() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1, "notes1").map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartSuccess() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1, "notes1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartError() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } throws IllegalStateException("Mock Error")
            repo.createCart(mockProduct, 1, "notes1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartErrorNoId() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns null

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1, "notes1").map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
            }
        }
    }

    @Test
    fun createCartNotesNullLoading() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                assertEquals(null, actualData.payload)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartNotesNullSuccess() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartNotesNullError() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns "1"

        runTest {
            coEvery {
                ds.insertCart(any())
            } throws IllegalStateException("Mock Error")
            repo.createCart(mockProduct, 1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.insertCart(any()) }
            }
        }
    }

    @Test
    fun createCartNotesNullErrorNoId() {
        val mockProduct = mockk<Product>(relaxed = true)
        every {
            mockProduct.id
        } returns null

        runTest {
            coEvery {
                ds.insertCart(any())
            } returns 1
            repo.createCart(mockProduct, 1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
            }
        }
    }

    @Test
    fun decreaseCartDeleteCartLoading() {
        coEvery {
            ds.deleteCart(any())
        } returns 1

        runTest {
            repo.decreaseCart(mockCart1).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCartDeleteCartSuccess() {
        coEvery {
            ds.deleteCart(any())
        } returns 1

        runTest {
            repo.decreaseCart(mockCart1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCartDeleteCartError() {
        coEvery {
            ds.deleteCart(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.decreaseCart(mockCart1).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCartUpdateCartLoading() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCartUpdateCartSuccess() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCartUpdateCartError() {
        coEvery {
            ds.updateCart(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCartLoading() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCartSuccess() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCartError() {
        coEvery {
            ds.updateCart(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotesLoading() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotesSuccess() {
        coEvery {
            ds.updateCart(any())
        } returns 1

        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        coEvery {
            ds.updateCart(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCartLoading() {
        coEvery {
            ds.deleteCart(any())
        } returns 1

        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteCartSuccess() {
        coEvery {
            ds.deleteCart(any())
        } returns 1

        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteCartError() {
        coEvery {
            ds.deleteCart(any())
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.deleteCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAllCart() {
        runTest {
            coEvery {
                repo.deleteAllCart()
            } returns Unit

            val result = ds.deleteAll()
            coVerify {
                repo.deleteAllCart()
                assertEquals(Unit, result)
            }
        }
    }
}
