package com.arkan.aresto.data.datasource.category

import com.arkan.aresto.data.model.Category

interface CategoryDataSource {
    fun getCategoryList() : List<Category>
}