package com.arkan.aresto.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.repository.CartRepository
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.tools.MainCoroutineRule
import com.arkan.aresto.tools.getOrAwaitValue
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepo: CartRepository

    @MockK
    lateinit var userRepo: UserRepository

    private lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(cartRepo, userRepo))
    }

    @Test
    fun getAllCart() {
        every {
            cartRepo.getUserCartData()
        } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Pair(
                            listOf(
                                mockk(relaxed = true),
                                mockk(relaxed = true),
                            ),
                            8000.0,
                        ),
                    ),
                )
            }
        val result = viewModel.getAllCart().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun decreaseCart() {
        every {
            cartRepo.decreaseCart(any())
        } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify {
            cartRepo.decreaseCart(any())
        }
    }

    @Test
    fun increaseCart() {
        every {
            cartRepo.increaseCart(any())
        } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify {
            cartRepo.increaseCart(any())
        }
    }

    @Test
    fun removeCart() {
        every {
            cartRepo.deleteCart(any())
        } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify {
            cartRepo.deleteCart(any())
        }
    }

    @Test
    fun setCartNotes() {
        every {
            cartRepo.setCartNotes(any())
        } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify {
            cartRepo.setCartNotes(any())
        }
    }

    @Test
    fun isUserLoggedIn() {
        every {
            userRepo.isLoggedIn()
        } returns true

        val actualResult = viewModel.isUserLoggedIn()
        verify {
            userRepo.isLoggedIn()
        }
        assertEquals(true, actualResult)
    }
}
