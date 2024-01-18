package com.example.e_market.ui.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_market.MyApplication
import com.example.e_market.R
import com.example.e_market.adapter.ProductsAdapter
import com.example.e_market.databinding.FragmentHomeBinding
import com.example.e_market.models.FilterEnum
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.FilterCommunication
import com.example.e_market.ui.view.filterScreen.FilterDialogFragment
import com.example.e_market.ui.viewmodel.HomeViewModel
import com.example.e_market.ui.viewmodelprovider.HomeViewModelProviderFactory
import com.example.e_market.util.Resource
import java.text.SimpleDateFormat
import java.util.Locale

class HomeFragment : Fragment(), FilterCommunication {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var productsAdapter: ProductsAdapter
    private var productList: MutableList<ProductResponseItem> = arrayListOf()
    private var productsInShoppingBox: MutableList<ShoppingBoxItem> = arrayListOf()
    private var allFavouritedProductsList: MutableList<ProductResponseItem> = arrayListOf()
    private var isIncludedShoppingBoxProductsInDatabase = false
    private var selectedShoppingBoxItemQuantity = 1
    var searchQuery: String = ""


    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelProviderFactory(
            requireActivity().application,
            (requireActivity().application as MyApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initData()
        initListener()
        initObserver()
        return binding.root
    }


    private fun initData() {
        viewModel.getProducts()
        viewModel.getShoppinBoxProduct()
        viewModel.getAllFavouritedProject()
        setupRecyclerView()
    }

    private fun initListener() {
        productsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putParcelable("product", it)
            }

            Navigation.findNavController(this.requireView())
                .navigate(
                    R.id.action_homeFragment_to_detailFragment,
                    bundle
                )
        }

        productsAdapter.setFavouriteItemClickListener {
            viewModel.saveFavouriteProduct(it)
        }

        productsAdapter.setDeleteFavouriteItemClickListener {
            viewModel.deleteFavourite(it)
        }
        productsAdapter.setAddToCardItemClickListener {
            checkControlShoppingCardProduct(it)

            viewModel.addToCard(
                isIncludedShoppingBoxProductsInDatabase,
                product = it,
                quantity = selectedShoppingBoxItemQuantity
            )
            Toast.makeText(
                this.requireContext(),
                getString(R.string.added_to_card),
                Toast.LENGTH_SHORT
            ).show()
            viewModel.getShoppinBoxProduct()
        }

        binding.filterButton.setOnClickListener {

            val dialogFragment = FilterDialogFragment.newInstance(ArrayList(productList))
            dialogFragment.onAttachToParentFragment(this)
            dialogFragment.show(requireFragmentManager(), "FilterDialogFragment")
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        binding.swipeRefresh.setOnRefreshListener {
            binding.productsRv.visibility = View.GONE
            viewModel.getProducts()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun checkControlShoppingCardProduct(product: ProductResponseItem) {
        for (i in productsInShoppingBox) {
            if (i.id == product.id) {
                isIncludedShoppingBoxProductsInDatabase = true
                selectedShoppingBoxItemQuantity = i.quantity + 1
            } else {
                isIncludedShoppingBoxProductsInDatabase = false
            }
        }
    }

    private fun filterList(query: String?) {
        if (query != null && !productList.isNullOrEmpty()) {
            searchQuery = query
            val filteredList = ArrayList<ProductResponseItem>()

            for (i in productList) {
                if (i.name!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                productsAdapter.setItem(filteredList)
                productsAdapter.notifyDataSetChanged()
                binding.productsRv.smoothScrollToPosition(0)

            }
        }
    }

    private fun initObserver() {
        viewModel.productsResponse.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressLoading()
                    response.data?.let { productsResponse ->
                        binding.productsRv.visibility = View.VISIBLE
                        productList = productsResponse.toMutableList()
                        productsAdapter.apply {
                            setItem(showFavouritedProducts(productList))
                            notifyDataSetChanged()
                        }
                    }
                }

                is Resource.Error -> {
                    hideProgressLoading()
                    response.message?.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "An Error Occured: $message",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Loading -> {
                    showProgressLoading()
                }

                else -> {}
            }
        })

        viewModel.productsInShoppingBox.observe(viewLifecycleOwner, Observer { shopBoxItems ->
            if (!shopBoxItems.isNullOrEmpty()) {
                productsInShoppingBox = shopBoxItems.toMutableList()
            }
        })

        viewModel.allFavouritedProducts.observe(
            viewLifecycleOwner,
            Observer { allFavouritedProducts ->
                if (!allFavouritedProducts.isNullOrEmpty()) {
                    allFavouritedProductsList = allFavouritedProducts.toMutableList()
                }
            })

    }

    private fun showFavouritedProducts(list: MutableList<ProductResponseItem>): MutableList<ProductResponseItem> {
        if (!list.isNullOrEmpty() && !allFavouritedProductsList.isNullOrEmpty()) {
            for (product in list) {
                for (favouriteProduct in allFavouritedProductsList) {
                    if (product.id == favouriteProduct.id) {
                        product.isFavorited = favouriteProduct.isFavorited
                    }
                }
            }
        }
        return list
    }

    fun showProgressLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideProgressLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        productsAdapter = ProductsAdapter()
        binding.productsRv.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(this.context, 2)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", true)
    }

    override fun onFilterEnumSelected(value: FilterEnum) {
        applyFilter(value)
    }

    fun applyFilter(value: FilterEnum) {
        when (value) {
            FilterEnum.PRICELOWTOHIGH -> {
                filterLowToHigh()
            }

            FilterEnum.PRICEHIGHTTOLOW -> {
                filterHightToLow()
            }

            FilterEnum.OLDTONEW -> {
                filterOldToNewDate()
            }

            FilterEnum.NEWTOOLD -> {
                filterNewToOldDate()
            }

            else -> {}
        }
    }

    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private fun filterOldToNewDate() {
        val sortedListOld = productList.sortedBy { sdf.parse(it.createdAt) }
        productsAdapter.setItem(sortedListOld.toMutableList())
        productsAdapter.notifyDataSetChanged()

    }

    private fun filterNewToOldDate() {
        val sortedListOld = productList.sortedBy { sdf.parse(it.createdAt) }
        productsAdapter.setItem(sortedListOld.reversed().toMutableList())
        productsAdapter.notifyDataSetChanged()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterLowToHigh() {
        val sortedList = productList.sortedBy { it.price?.split(".")?.get(0)?.toInt() }
        productsAdapter.setItem(sortedList.toMutableList())
        productsAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filterHightToLow() {
        val sortedList = productList.sortedByDescending { it.price?.split(".")?.get(0)?.toInt() }
        productsAdapter.setItem(sortedList.toMutableList())
        productsAdapter.notifyDataSetChanged()
    }

}