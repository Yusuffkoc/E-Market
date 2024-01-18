package com.example.e_market.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.e_market.databinding.ShoppingBoxRowBinding
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem

class ShoppingBoxAdapter : RecyclerView.Adapter<ShoppingBoxAdapter.ProductsViewHolder>() {
    var rowPosition = 0

    inner class ProductsViewHolder(private val binding: ShoppingBoxRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        fun bind(product: ShoppingBoxItem) {
            binding.productName.text = product.name
            binding.productPrice.text = product.price + " ₺"
            binding.quantity.text = product.quantity.toString()
            val value = binding.quantity.text.toString()
            binding.minus.setOnClickListener {
                //minus quantity in databse
                if (value.toInt() > 0) {
                    binding.quantity.text =
                        (value.toInt() - 1).toString()
                    notifyItemChanged(rowPosition)
                    onItemQuantityClickListener?.let { it(product, false) }
                }
            }

            binding.plus.setOnClickListener {
                //plus quantity in databse
                binding.quantity.text = (value.toInt() + 1).toString()
                notifyItemChanged(rowPosition)
                onItemQuantityClickListener?.let { it(product, true) }
            }

        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingBoxItem>() {
        override fun areItemsTheSame(
            oldItem: ShoppingBoxItem,
            newItem: ShoppingBoxItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingBoxItem,
            newItem: ShoppingBoxItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            ShoppingBoxRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemQuantityClickListener: ((ShoppingBoxItem, Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        rowPosition = position
        val product = differ.currentList[position]
        holder.bind(product = product)
    }

    fun setItemQuantityClickListener(listener: (ShoppingBoxItem, Boolean) -> Unit) {
        onItemQuantityClickListener = listener
    }

}