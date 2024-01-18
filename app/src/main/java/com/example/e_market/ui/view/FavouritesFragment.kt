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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_market.MyApplication
import com.example.e_market.adapter.FavouritesAdapter
import com.example.e_market.databinding.FragmentFavouritesBinding
import com.example.e_market.ui.viewmodel.FavouritesViewModel
import com.example.e_market.ui.viewmodelprovider.FavouritesViewModelProviderFactory

class FavouritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favouritesAdapter: FavouritesAdapter
    private val viewModel: FavouritesViewModel by viewModels {
        FavouritesViewModelProviderFactory(
            (requireActivity().application as MyApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        initData()
        initObserver()
        initListener()
        return binding.root
    }

    private fun initListener() {
        favouritesAdapter.setFavouriteItemClickListener {
            viewModel.saveFavouriteProduct(it)
        }

        favouritesAdapter.setDeleteFavouriteItemClickListener {
            viewModel.deleteFavourite(it)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFavouritesProduct()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun initData() {
        setupRecyclerView()
        viewModel.getFavouritesProduct()
    }

    private fun setupRecyclerView() {
        favouritesAdapter = FavouritesAdapter()
        binding.productsRv.apply {
            adapter = favouritesAdapter
            layoutManager = GridLayoutManager(this.context,2)
            itemAnimator = null
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initObserver() {
        viewModel.productsResponse.observe(viewLifecycleOwner, Observer { products ->
            if (!products.isNullOrEmpty()) {
                favouritesAdapter.setItem(products.toMutableList())
                favouritesAdapter.notifyDataSetChanged()
            } else {
                favouritesAdapter.clearData()
                Toast.makeText(this.requireContext(),"Favori Ürününüz Bulunmamaktadır...", Toast.LENGTH_SHORT).show()
            }

        })
    }

}