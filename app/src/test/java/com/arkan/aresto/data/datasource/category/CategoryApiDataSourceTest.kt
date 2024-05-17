package com.arkan.aresto.data.datasource.category

import com.arkan.aresto.data.source.network.model.category.CategoriesResponse
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

class CategoryApiDataSourceTest {
    @MockK
    lateinit var service: ArestoApiService

    private lateinit var dataSource: CategoryDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = CategoryApiDataSource(service)
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoriesResponse>(relaxed = true)
            coEvery {
                service.getCategories()
            } returns mockResponse

            val actualResult = dataSource.getCategoryList()
            coVerify {
                service.getCategories()
            }
            assertEquals(actualResult, mockResponse)
        }
    }
}
