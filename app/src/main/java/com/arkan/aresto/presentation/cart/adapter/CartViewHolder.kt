package com.arkan.aresto.presentation.cart.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.databinding.ItemCartProductBinding
import com.arkan.aresto.utils.doneEditing
import com.arkan.aresto.utils.toIndonesianFormat

class CartViewHolder(
    private val binding: ItemCartProductBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            ivProductImage.load(item.productImgUrl) {
                crossfade(true)
            }
            tvQtyProduct.text = item.productQty.toString()
            tvProductName.text = item.productName
            tvProductPrice.text = (item.productQty * item.productPrice).toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesItem.setText(item.productNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem = item.copy().apply {
                productNotes = binding.etNotesItem.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            btnMinusCart.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            btnPlusCart.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            btnRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}