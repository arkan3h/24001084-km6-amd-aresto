package com.arkan.aresto.presentation.home

import androidx.lifecycle.ViewModel
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.ProductRepository

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    fun getProduct() = productRepository.getProducts()
    fun getCategory() = categoryRepository.getCategories()
}