package com.arkan.aresto.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arkan.aresto.data.repository.CartRepository
import com.arkan.aresto.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun checkoutCart() =
        productRepository.createOrder(
            checkoutData.value?.payload?.first.orEmpty(),
        ).asLiveData(Dispatchers.IO)

    fun removeCart() {
        viewModelScope.launch {
            cartRepository.deleteAllCart()
        }
    }
}
