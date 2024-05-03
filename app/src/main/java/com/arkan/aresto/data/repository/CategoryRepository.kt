package com.arkan.aresto.data.repository

import com.arkan.aresto.data.datasource.category.CategoryDataSource
import com.arkan.aresto.data.mapper.toCategories
import com.arkan.aresto.data.model.Category
import com.arkan.aresto.utils.ResultWrapper
import com.arkan.aresto.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategoryList().data.toCategories() }
    }
}
