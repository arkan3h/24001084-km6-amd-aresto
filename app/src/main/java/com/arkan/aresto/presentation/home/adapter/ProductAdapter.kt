package com.arkan.aresto.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arkan.aresto.base.OnItemCLickedListener
import com.arkan.aresto.base.ViewHolderBinder
import com.arkan.aresto.data.model.Product
import com.arkan.aresto.databinding.ItemProductGridBinding
import com.arkan.aresto.databinding.ItemProductListBinding

class ProductAdapter(
    private val listener : OnItemCLickedListener<Product>,
    private val listMode: Int = MODE_LIST
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private val asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    )

    fun submitData(items: List<Product>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(listMode == MODE_GRID) ProductGridViewHolder(
            ItemProductGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        ) else ProductListViewHolder(
            ItemProductListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun getItemCount(): Int  = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Product>).bind(asyncDataDiffer.currentList[position])
    }
}