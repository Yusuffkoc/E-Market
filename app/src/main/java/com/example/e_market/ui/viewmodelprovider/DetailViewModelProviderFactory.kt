package com.example.e_market.ui.viewmodelprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.DetailViewModel

class DetailViewModelProviderFactory (
    private val productsRepository: ProductRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(productsRepository) as T
    }
}