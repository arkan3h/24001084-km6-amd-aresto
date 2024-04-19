package com.arkan.aresto.data.mapper

import com.arkan.aresto.data.model.Category
import com.arkan.aresto.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        name = this?.name.orEmpty(),
        imgUrl = this?.imgUrl.orEmpty(),
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()