package com.arkan.aresto.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.ProductRepository
import com.arkan.aresto.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    fun getProduct(categorySlug: String? = null) = productRepository.getProducts(categorySlug).asLiveData(Dispatchers.IO)

    fun getCategory() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

    fun isUsingGrid() = userRepository.isUsingGrid()

    fun setUsingGridMode(isUsingGrid: Boolean) = userRepository.setUsingGridMode(isUsingGrid)
}
