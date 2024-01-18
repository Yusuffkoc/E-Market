package com.example.e_market.ui.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.FavouritesViewModel

class FavouritesViewModelProviderFactory (
    private val productsRepository: ProductRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavouritesViewModel(productsRepository) as T
    }
}