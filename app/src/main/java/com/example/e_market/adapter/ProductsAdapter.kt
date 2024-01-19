package com.example.e_market.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_market.databinding.ProductsRowBinding
import com.example.e_market.models.ProductResponseItem

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {


    private var itemList: MutableList<ProductResponseItem> = mutableListOf()
    private var alreadyFavourited = false
    inner class ProductsViewHolder(private val binding: ProductsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(product: ProductResponseItem) {
            Glide.with(binding.root).load(product.image).into(binding.productImage)
            binding.productName.text = product.name
            binding.productPrice.text = product.price + " ₺"
            if (product.isFavorited){
                binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
            }
            binding.root.setOnClickListener {
                onItemClickListener?.let { it(product) }
            }
            binding.favouriteStar.setOnClickListener {
                if (binding.favouriteStar.isChecked) {
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                    product.isFavorited = true
                    alreadyFavourited = false
                    favouriteItemClickListener?.let { it(product) }
                } else {
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
                    product.isFavorited = false
                    alreadyFavourited = true
                    deleteFavouriteItemClickListener?.let { it(product) }
                }
                notifyItemChanged(adapterPosition)
            }
            binding.addToCard.setOnClickListener {
                addToCardItemClickListener?.let { it(product) }
            }
        }
    }

    fun setItem(list: MutableList<ProductResponseItem>) {
        itemList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ProductsRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private var onItemClickListener: ((ProductResponseItem) -> Unit)? = null
    private var favouriteItemClickListener: ((ProductResponseItem) -> Unit)? = null
    private var deleteFavouriteItemClickListener: ((ProductResponseItem) -> Unit)? = null
    private var addToCardItemClickListener: ((ProductResponseItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = itemList[position]
        holder.bind(product = product)
    }


    fun setOnItemClickListener(listener: (ProductResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    fun setFavouriteItemClickListener(listener: (ProductResponseItem) -> Unit) {
        favouriteItemClickListener = listener
    }

    fun setDeleteFavouriteItemClickListener(listener: (ProductResponseItem) -> Unit) {
        deleteFavouriteItemClickListener = listener
    }

    fun setAddToCardItemClickListener(listener: (ProductResponseItem) -> Unit) {
        addToCardItemClickListener = listener
    }

}