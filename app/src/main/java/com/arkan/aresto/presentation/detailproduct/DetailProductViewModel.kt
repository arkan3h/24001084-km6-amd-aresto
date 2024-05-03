package com.arkan.aresto.presentation.detailproduct

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.data.repository.CartRepository
import com.arkan.aresto.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalStateException

class DetailProductViewModel(
    extras: Bundle?,
    private val cartRepository: CartRepository,
) : ViewModel() {
    val product = extras?.getParcelable<Product>(DetailProductActivity.EXTRAS_ITEM_ACT)
    val productQty =
        MutableLiveData(1).apply {
            postValue(1)
        }
    var totalPrice =
        MutableLiveData<Double>().apply {
            postValue(product?.price)
        }

    fun addQtyProduct() {
        val count = (productQty.value ?: 0) + 1
        productQty.postValue(count)
        totalPrice.postValue(product?.price?.times(count) ?: 0.0)
    }

    fun removeQtyProduct() {
        if ((productQty.value ?: 0) > 1) {
            val count = (productQty.value ?: 0) - 1
            productQty.postValue(count)
            totalPrice.postValue(product?.price?.times(count) ?: 0.0)
        } else {
            productQty.postValue(1)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return product?.let {
            val quantity = productQty.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Product not found"))) }
    }
}
