package com.arkan.aresto.data.datasource.category

import com.arkan.aresto.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategoryList() : CategoriesResponse
}