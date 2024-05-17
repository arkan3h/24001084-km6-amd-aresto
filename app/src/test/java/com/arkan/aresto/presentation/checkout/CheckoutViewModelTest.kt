package com.arkan.aresto.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.data.model.ProductPrice
import com.arkan.aresto.data.repository.CartRepository
import com.arkan.aresto.data.repository.ProductRepository
import com.arkan.aresto.tools.MainCoroutineRule
import com.arkan.aresto.tools.getOrAwaitValue
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CheckoutViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepo: CartRepository

    @MockK
    lateinit var productRepo: ProductRepository

    private lateinit var viewModel: CheckoutViewModel

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
    private val mockListCart = listOf(mockCart, mockCart1)
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
        every {
            cartRepo.getCheckoutData()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockListCart,
                            mockProductPrice,
                            8000.0,
                        ),
                    ),
                )
            }
        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepo,
                    productRepo,
                ),
            )
    }

    @Test
    fun checkoutCart() {
        every {
            productRepo.createOrder(any())
        } returns
            flow {
                emit(
                    ResultWrapper.Success(true),
                )
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(true, result.payload)
        verify {
            productRepo.createOrder(any())
        }
    }

    @Test
    fun removeCart() {
        coEvery {
            cartRepo.deleteAllCart()
        } returns Unit
        val result = viewModel.removeCart()
        coVerify {
            cartRepo.deleteAllCart()
        }
        assertEquals(Unit, result)
    }
}
