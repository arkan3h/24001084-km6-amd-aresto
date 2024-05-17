package com.arkan.aresto.presentation.detailproduct

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.repository.CartRepository
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

class DetailProductViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: CartRepository

    @MockK
    lateinit var extras: Bundle

    private lateinit var viewModel: DetailProductViewModel
    private val mockProduct = mockk<Product>(relaxed = true)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { extras.getParcelable<Product>(DetailProductActivity.EXTRAS_ITEM_ACT) } returns mockProduct
        viewModel = spyk(DetailProductViewModel(extras, repo))
    }

    @Test
    fun addToCart() {
        every {
            repo.createCart(mockProduct, 1)
        } returns
            flow {
                emit(
                    ResultWrapper.Success(true),
                )
            }
        val result = viewModel.addToCart().getOrAwaitValue()
        assertEquals(true, result.payload)
        verify { repo.createCart(mockProduct, 1) }
    }
}
