package com.arkan.aresto.presentation.detailproduct

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.aresto.data.model.Product

class DetailProductViewModel(
    private val extras: Bundle?,
) : ViewModel() {
    val product = extras?.getParcelable<Product>(DetailProductActivity.EXTRAS_ITEM_ACT)
    val productQty = MutableLiveData(1).apply {
        postValue(1)
    }
    var totalPrice = MutableLiveData<Double>().apply {
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
        } else productQty.postValue(1)
    }
}