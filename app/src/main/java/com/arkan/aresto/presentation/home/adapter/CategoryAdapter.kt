package com.arkan.aresto.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arkan.aresto.data.model.Category
import com.arkan.aresto.databinding.ItemCategoryBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){
    private val asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    )

    fun submitData(items: List<Category>) {
        asyncDataDiffer.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    //counting data size
    override fun getItemCount(): Int  = asyncDataDiffer.currentList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(asyncDataDiffer.currentList[position])
    }

    class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category) {
            binding.ivCategoryImage.load(item.imgUrl)
            binding.tvCategoryName.text = item.name
        }
    }
}