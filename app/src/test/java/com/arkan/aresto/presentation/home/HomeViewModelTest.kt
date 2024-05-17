package com.arkan.aresto.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.ProductRepository
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

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepo: UserRepository

    @MockK
    lateinit var categoryRepo: CategoryRepository

    @MockK
    lateinit var productRepo: ProductRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            spyk(
                HomeViewModel(
                    userRepo,
                    categoryRepo,
                    productRepo,
                ),
            )
    }

    @Test
    fun getProduct() {
        every { productRepo.getProducts() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(), mockk(),
                        ),
                    ),
                )
            }
        val result = viewModel.getProduct().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { productRepo.getProducts() }
    }

    @Test
    fun getProductSlug() {
        every { productRepo.getProducts(any()) } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(), mockk(),
                        ),
                    ),
                )
            }
        val result = viewModel.getProduct().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { productRepo.getProducts(any()) }
    }

    @Test
    fun getCategory() {
        every { categoryRepo.getCategories() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        listOf(
                            mockk(), mockk(),
                        ),
                    ),
                )
            }
        val result = viewModel.getCategory().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepo.getCategories() }
    }

    @Test
    fun isUsingGrid() {
        every {
            userRepo.isUsingGrid()
        } returns true
        val result = viewModel.isUsingGrid()
        assertEquals(true, result)
        verify { userRepo.isUsingGrid() }
    }

    @Test
    fun setUsingGridMode() {
        every {
            userRepo.setUsingGridMode(any())
        } returns Unit
        viewModel.setUsingGridMode(false)
        verify {
            userRepo.setUsingGridMode(any())
        }
    }
}
