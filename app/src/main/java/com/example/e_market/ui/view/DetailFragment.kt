package com.example.e_market.ui.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.e_market.MainActivity
import com.example.e_market.MyApplication
import com.example.e_market.R
import com.example.e_market.databinding.FragmentDetailBinding
import com.example.e_market.models.MenuEnum
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.ui.viewmodel.DetailViewModel
import com.example.e_market.ui.viewmodelprovider.DetailViewModelProviderFactory

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var binding: FragmentDetailBinding
    private var product: ProductResponseItem? = null
    private var quantityInShoppingBox = 0
    private var isIncludedInDatabase = false

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelProviderFactory(
            (requireActivity().application as MyApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        initData()
        initListener()
        initObserver()
        return binding.root
    }

    private fun initData() {
        product = arguments?.getParcelable<ProductResponseItem>("product")
        product?.let { product ->
            Glide.with(binding.root).load(product.image)
                .into(binding.productImage)
            binding.productName.text = product.name
            binding.titleProductName.text = product.name
            binding.productDetail.text = product.description
            binding.productPrice.text = product.price + " ₺"
            if (product.isFavorited) {
                binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
            }
            viewModel.getSpecificProductsInShoppingBox(product.id)
        }

    }

    private fun initListener() {

        binding.addToCard.setOnClickListener {
            product?.let {
                viewModel.saveProductToDatabase(
                    isIncludedInDatabase,
                    it,
                    quantityInShoppingBox
                )
            }

            (activity as MainActivity).setMenuItem(MenuEnum.SHOPPINBOX.id)

            val bundle = Bundle().apply {
                product?.let { putInt("productId", it.id) }
            }
            Navigation.findNavController(this.requireView())
                .navigate(R.id.action_detailFragment_to_shoppingBoxFragment, bundle)
        }

        binding.favouriteStar.setOnClickListener {
            product?.let { product ->
                if (binding.favouriteStar.isChecked) {
                    product.isFavorited = true
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                    viewModel.saveFavouriteProduct(product)
                } else {
                    product.isFavorited = false
                    binding.favouriteStar.backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                    viewModel.deleteFavourite(product)
                }
            }
        }

        binding.backIcon.setOnClickListener {
            Navigation.findNavController(this.requireView()).popBackStack()
        }

    }

    private fun initObserver() {
        viewModel.specificProductsInShoppingBox.observe(
            viewLifecycleOwner,
            Observer { productInShoppinBox ->
                if (productInShoppinBox != null) {
                    isIncludedInDatabase = true
                    quantityInShoppingBox = productInShoppinBox.quantity + 1
                } else {
                    isIncludedInDatabase = false
                    quantityInShoppingBox = 1
                }
            })

    }


}