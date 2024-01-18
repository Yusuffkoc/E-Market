package com.example.e_market.ui.viewmodelprovider

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.HomeViewModel

class HomeViewModelProviderFactory (
    val app: Application,
    private val productsRepository: ProductRepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(app, productsRepository) as T
    }
}