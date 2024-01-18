package com.example.e_market.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class FavouritesViewModel(
    val repository: ProductRepositoryImpl
) : ViewModel() {

    val productsResponse: MutableLiveData<List<ProductResponseItem>> = MutableLiveData()

    fun getFavouritesProduct() = viewModelScope.launch {
        getSafeFavouritesProduct()
    }

    private suspend fun getSafeFavouritesProduct() {
        try {
            val response = repository.getFavouritedProducts()
            productsResponse.postValue(response)

        } catch (t: Throwable) {
            Log.d("ShoppingBoxViewModel Error : ", "${t.message}")
        }
    }

    fun deleteFavourite(product: ProductResponseItem) = viewModelScope.launch {
        deleteFavouriteProduct(product)
    }

    private suspend fun deleteFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.deleteProductInFavourites(product)
    }

    fun saveFavouriteProduct(product: ProductResponseItem) = viewModelScope.launch {
        saveSafeCallFavouriteProduct(product)
    }

    private suspend fun saveSafeCallFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.insertFavouritedProduct(product)
    }

}