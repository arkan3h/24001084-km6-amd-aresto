package com.arkan.aresto.base

import com.arkan.aresto.data.model.Product

interface ViewHolderBinder<T> {
    fun bind(item:Product)
}