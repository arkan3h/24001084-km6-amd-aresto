package com.arkan.aresto.presentation.checkout.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.aresto.R
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.databinding.ItemCartProductOrderBinding
import com.arkan.aresto.utils.toIndonesianFormat

class CheckoutViewHolder(
    private val binding: ItemCartProductOrderBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            ivProductImage.load(item.productImgUrl)
            tvQuantityProduct.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.productQty.toString(),
                )
            tvProductName.text = item.productName
            tvProductPrice.text = (item.productQty * item.productPrice).toIndonesianFormat()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.productNotes
    }
}
