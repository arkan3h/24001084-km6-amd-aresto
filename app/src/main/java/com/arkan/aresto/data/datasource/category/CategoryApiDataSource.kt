package com.arkan.aresto.data.datasource.category

import com.arkan.aresto.data.source.network.model.category.CategoriesResponse
import com.arkan.aresto.data.source.network.services.ArestoApiService

class CategoryApiDataSource(
    private val service: ArestoApiService
) : CategoryDataSource {
    override suspend fun getCategoryList(): CategoriesResponse {
        return service.getCategories()
    }
}