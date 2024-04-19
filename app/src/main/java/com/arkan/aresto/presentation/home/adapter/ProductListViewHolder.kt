package com.arkan.aresto.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.aresto.base.OnItemCLickedListener
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.databinding.ItemProductListBinding
import com.arkan.aresto.utils.toIndonesianFormat

class ProductListViewHolder(
    private val binding: ItemProductListBinding,
    private val listener: OnItemCLickedListener<Product>
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Product> {
    override fun bind(item: Product) {
        item.let {
            binding.ivProductImage.load(it.imgUrl)
            binding.tvProductName.text = it.name
            binding.tvProductPrice.text = item.price.toIndonesianFormat()
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}