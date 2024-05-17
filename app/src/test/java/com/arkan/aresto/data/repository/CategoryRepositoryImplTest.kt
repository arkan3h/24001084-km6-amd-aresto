package com.arkan.aresto.data.repository

import app.cash.turbine.test
import com.arkan.aresto.data.datasource.category.CategoryDataSource
import com.arkan.aresto.data.source.network.model.category.CategoriesResponse
import com.arkan.aresto.data.source.network.model.category.CategoryItemResponse
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

class CategoryRepositoryImplTest {
    @MockK
    lateinit var ds: CategoryDataSource

    private lateinit var repo: CategoryRepository

    private val cart1 =
        CategoryItemResponse(
            name = "1",
            imgUrl = "1",
        )
    private val cart2 =
        CategoryItemResponse(
            name = "2",
            imgUrl = "2",
        )
    private val mockListCategory = listOf(cart1, cart2)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CategoryRepositoryImpl(ds)
    }

    @Test
    fun getCategoriesLoading() {
        val mockResponse = mockk<CategoriesResponse>()
        every {
            mockResponse.data
        } returns mockListCategory

        runTest {
            coEvery {
                ds.getCategoryList()
            } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(150)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify {
                    ds.getCategoryList()
                }
            }
        }
    }

    @Test
    fun getCategoriesSuccess() {
        val mockResponse = mockk<CategoriesResponse>()
        every {
            mockResponse.data
        } returns mockListCategory

        runTest {
            coEvery {
                ds.getCategoryList()
            } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify {
                    ds.getCategoryList()
                }
            }
        }
    }

    @Test
    fun getCategoriesError() {
        runTest {
            coEvery {
                ds.getCategoryList()
            } throws IllegalStateException("Mock Error")
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify {
                    ds.getCategoryList()
                }
            }
        }
    }

    @Test
    fun getCategoriesEmpty() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every {
            mockResponse.data
        } returns mockListCategory

        runTest {
            coEvery {
                ds.getCategoryList()
            } returns mockResponse
            repo.getCategories().map {
                delay(100)
                it
            }.test {
                delay(250)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify {
                    ds.getCategoryList()
                }
            }
        }
    }
}
