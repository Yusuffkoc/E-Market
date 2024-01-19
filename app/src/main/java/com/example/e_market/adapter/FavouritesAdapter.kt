package com.example.e_market.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_market.databinding.FavouritesProductsRowBinding
import com.example.e_market.databinding.ProductsRowBinding
import com.example.e_market.models.ProductResponseItem

class FavouritesAdapter : RecyclerView.Adapter<FavouritesAdapter.ProductsViewHolder>() {

    private var itemList:MutableList<ProductResponseItem> = mutableListOf()

    inner class ProductsViewHolder(private val binding: FavouritesProductsRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductResponseItem) {
            Glide.with(binding.root).load(product.image).into(binding.productImage)
            binding.productName.text = product.name
            binding.productPrice.text = product.price + " ₺"
            if (product.isFavorited){
                binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
            }else{
                binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
            }

            binding.favouriteStar.setOnClickListener {
                product.isFavorited = !product.isFavorited
                if(product.isFavorited){
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                    favouriteItemClickListener?.let { it(product) }
                }
                else{
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    deleteFavouriteItemClickListener?.let { it(product) }
                }
                notifyItemChanged(adapterPosition)
            }
        }
    }

    fun setItem(list: MutableList<ProductResponseItem>){
        itemList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            FavouritesProductsRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    private var favouriteItemClickListener: ((ProductResponseItem) -> Unit)? = null
    private var deleteFavouriteItemClickListener: ((ProductResponseItem) -> Unit)? = null

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = itemList[position]
        holder.bind(product = product)
    }

    fun setFavouriteItemClickListener(listener: (ProductResponseItem) -> Unit) {
        favouriteItemClickListener = listener
    }

    fun setDeleteFavouriteItemClickListener(listener: (ProductResponseItem) -> Unit) {
        deleteFavouriteItemClickListener = listener
    }

    fun clearData() {
        if (!itemList.isNullOrEmpty()){
            itemList.clear()
            notifyDataSetChanged()
        }

    }

}