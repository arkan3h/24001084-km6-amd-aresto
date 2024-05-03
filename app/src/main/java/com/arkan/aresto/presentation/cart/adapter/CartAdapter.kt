package com.arkan.aresto.presentation.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.databinding.ItemCartProductBinding

class CartAdapter(
    private val cartListener: CartListener? = null,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val asyncDataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.productId == newItem.productId
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Cart>) {
        asyncDataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return CartViewHolder(
            ItemCartProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
            cartListener,
        )
    }

    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Cart>).bind(asyncDataDiffer.currentList[position])
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)

    fun onMinusTotalItemCartClicked(cart: Cart)

    fun onRemoveCartClicked(cart: Cart)

    fun onUserDoneEditingNotes(cart: Cart)
}
