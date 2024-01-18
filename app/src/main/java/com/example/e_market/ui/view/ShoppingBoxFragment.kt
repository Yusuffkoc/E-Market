package com.example.e_market.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_market.MyApplication
import com.example.e_market.R
import com.example.e_market.adapter.ShoppingBoxAdapter
import com.example.e_market.databinding.FragmentShoppingBoxBinding
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.ui.viewmodel.ShoppingBoxViewModel
import com.example.e_market.ui.viewmodelprovider.ShoppingBoxViewModelProviderFactory

class ShoppingBoxFragment : Fragment() {

    companion object {
        fun newInstance() = ShoppingBoxFragment()
    }

    private lateinit var binding: FragmentShoppingBoxBinding
    private lateinit var shoppingBoxAdapter: ShoppingBoxAdapter
    private var shoppingBoxProductsList: MutableList<ShoppingBoxItem> = mutableListOf()
    private var productId: Int? = null
    private var totalPrices = 0

    private val viewModel: ShoppingBoxViewModel by viewModels {
        ShoppingBoxViewModelProviderFactory(
            (requireActivity().application as MyApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingBoxBinding.inflate(inflater, container, false)
        initData()
        initListener()
        initObservers()
        return binding.root
    }

    private fun initData() {
        productId = arguments?.getInt("productId")
        viewModel.getShoppinBoxProduct()
        setupRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListener() {
        shoppingBoxAdapter.setItemQuantityClickListener { shoppingBoxItem, plusQuantity ->
            if (plusQuantity) {
                val lastQuantity = shoppingBoxItem.quantity + 1
                shoppingBoxItem.quantity = lastQuantity
                shoppingBoxProductsList.forEach { item ->
                    if (item.id == shoppingBoxItem.id) {
                        item.quantity = lastQuantity
                    }
                }
            } else {
                shoppingBoxItem.quantity -= 1
                if (shoppingBoxItem.quantity == 0) {
                    viewModel.deleteProductInCard(shoppingBoxItem.id)
                    shoppingBoxProductsList.remove(shoppingBoxItem)
                }
            }
            viewModel.updateProductQuantity(shoppingBoxItem)
            if (!shoppingBoxProductsList.isNullOrEmpty()) {
                shoppingBoxAdapter.differ.submitList(shoppingBoxProductsList)
            }
            setTotalPrice(shoppingBoxProductsList)
        }

        binding.addToCard.setOnClickListener {
            Toast.makeText(this.requireContext(), getString(R.string.payment_continue), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initObservers() {
        viewModel.productsResponse.observe(viewLifecycleOwner, Observer { shopBoxItems ->
            if (!shopBoxItems.isNullOrEmpty()) {
                setTotalPrice(shopBoxItems)
                shoppingBoxProductsList = shopBoxItems.toMutableList()
                shoppingBoxAdapter.differ.submitList(shopBoxItems)
            } else {
                Toast.makeText(
                    this.requireContext(),
                    getString(R.string.emptyBox),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalPrice(shopBoxItems: List<ShoppingBoxItem>) {
        totalPrices = 0
        for (i in shopBoxItems) {
            i.price?.let {
                var price = i.price.split(".").get(0).toInt()
                totalPrices += (price * i.quantity)
            }
        }
        binding.productPrice.text = totalPrices.toString() + getString(R.string.tl)
    }

    private fun setupRecyclerView() {
        shoppingBoxAdapter = ShoppingBoxAdapter()
        binding.shoppingBoxRv.apply {
            adapter = shoppingBoxAdapter
            layoutManager = LinearLayoutManager(this.context)
            itemAnimator = null
        }
    }

}