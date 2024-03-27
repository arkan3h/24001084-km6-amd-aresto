package com.arkan.aresto.data.datasource

import com.arkan.aresto.data.model.Category

interface CategoryDataSource {
    fun getCategoryList() : List<Category>
}