package com.arkan.aresto.presentation.checkout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.aresto.data.model.ProductPrice
import com.arkan.aresto.databinding.ItemPriceBinding
import com.arkan.aresto.utils.toIndonesianFormat

class PriceListAdapter(private val itemClick: (ProductPrice) -> Unit) :
    RecyclerView.Adapter<PriceListAdapter.PriceItemViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<ProductPrice>() {
                override fun areItemsTheSame(
                    oldItem: ProductPrice,
                    newItem: ProductPrice
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: ProductPrice,
                    newItem: ProductPrice
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            }
        )

    fun submitData(data: List<ProductPrice>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceItemViewHolder {
        val binding = ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PriceItemViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PriceItemViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size


    class PriceItemViewHolder(
        private val binding: ItemPriceBinding,
        val itemClick: (ProductPrice) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: ProductPrice) {
            with(item) {
                binding.tvItemPrice.text = item.total.toIndonesianFormat()
                binding.tvItemTitle.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }

        }
    }
}