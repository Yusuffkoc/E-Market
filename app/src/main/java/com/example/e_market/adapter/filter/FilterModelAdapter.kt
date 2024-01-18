package com.example.e_market.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_market.databinding.BrandRowBinding
import com.example.e_market.models.ProductResponseItem

class FilterModelAdapter : RecyclerView.Adapter<FilterModelAdapter.ProductsViewHolder>() {

    private var itemList: ArrayList<String> = ArrayList()
    private var selectedPositions = mutableSetOf<Int>()

    inner class ProductsViewHolder(private val binding: BrandRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.brandType.setOnClickListener {
                if (selectedPositions.contains(adapterPosition)) {
                    binding.brandType.isChecked = false
                    selectedPositions.remove(adapterPosition)
                } else {
                    binding.brandType.isChecked = true
                    selectedPositions.add(adapterPosition)
                }
            }
        }

        fun bind(model: String) {
            binding.brandName.text = model
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            BrandRowBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    fun setItemList(brandList: ArrayList<String>) {
        this.itemList = brandList
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val product = itemList[position]
        holder.bind(model = product)
    }

}