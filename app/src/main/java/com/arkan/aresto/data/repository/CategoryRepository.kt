package com.arkan.aresto.data.repository

import com.arkan.aresto.data.datasource.CategoryDataSource
import com.arkan.aresto.data.model.Category

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CategoryRepository {
    fun getCategories(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository {
    override fun getCategories(): List<Category> = dataSource.getCategoryList()
}