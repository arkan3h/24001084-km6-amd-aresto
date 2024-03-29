package com.arkan.aresto.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.aresto.R
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Cart
import com.arkan.aresto.databinding.ItemCartProductBinding
import com.arkan.aresto.databinding.ItemCartProductOrderBinding
import com.arkan.aresto.utils.doneEditing
import com.arkan.aresto.utils.toIndonesianFormat

//class CartListAdapter(
//    private val cartListener: CartListener? = null
//) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    private val asyncDataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>() {
//        override fun areItemsTheSame(
//            oldItem: Cart,
//            newItem: Cart
//        ): Boolean {
//            return oldItem.id == newItem.id && oldItem.productId == newItem.productId
//        }
//
//        override fun areContentsTheSame(
//            oldItem: Cart,
//            newItem: Cart
//        ): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//    })
//
//    fun submitData(data: List<Cart>) {
//        asyncDataDiffer.submitList(data)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return CartViewHolder(
//            ItemCartProductBinding.inflate(
//                LayoutInflater.from(parent.context), parent, false
//            ), cartListener
//        )
//    }
//
//    override fun getItemCount(): Int = asyncDataDiffer.currentList.size
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        (holder as ViewHolderBinder<Cart>).bind(asyncDataDiffer.currentList[position])
//    }
//}

//class CartViewHolder(
//    private val binding: ItemCartProductBinding,
//    private val cartListener: CartListener?
//) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
//    override fun bind(item: Cart) {
//        setCartData(item)
//        setCartNotes(item)
//        setClickListeners(item)
//    }
//
//    private fun setCartData(item: Cart) {
//        with(binding) {
//            ivProductImage.load(item.productImgUrl) {
//                crossfade(true)
//            }
//            tvQtyProduct.text = item.productQty.toString()
//            tvProductName.text = item.productName
//            tvProductPrice.text = (item.productQty * item.productPrice).toIndonesianFormat()
//        }
//    }
//
//    private fun setCartNotes(item: Cart) {
//        binding.etNotesItem.setText(item.productNotes)
//        binding.etNotesItem.doneEditing {
//            binding.etNotesItem.clearFocus()
//            val newItem = item.copy().apply {
//                productNotes = binding.etNotesItem.text.toString().trim()
//            }
//            cartListener?.onUserDoneEditingNotes(newItem)
//        }
//    }
//
//    private fun setClickListeners(item: Cart) {
//        with(binding) {
//            btnMinusCart.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
//            btnPlusCart.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
//            btnRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
//        }
//    }
//}

//class CartOrderViewHolder(
//    private val binding: ItemCartProductOrderBinding,
//) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart> {
//    override fun bind(item: Cart) {
//        setCartData(item)
//        setCartNotes(item)
//    }
//
//    private fun setCartData(item: Cart) {
//        with(binding) {
//            ivProductImage.load(item.productImgUrl)
//            tvQuantityProduct.text = itemView.rootView.context.getString(
//                R.string.total_quantity,
//                item.productQty.toString()
//            )
//            tvProductName.text = item.productName
//            tvProductPrice.text = (item.productQty * item.productPrice).toIndonesianFormat()
//        }
//    }
//
//    private fun setCartNotes(item: Cart) {
//        binding.tvNotes.text = item.productNotes
//    }
//}

//interface CartListener {
//    fun onPlusTotalItemCartClicked(cart: Cart)
//    fun onMinusTotalItemCartClicked(cart: Cart)
//    fun onRemoveCartClicked(cart: Cart)
//    fun onUserDoneEditingNotes(cart: Cart)
//}