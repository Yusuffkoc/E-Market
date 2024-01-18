package com.example.e_market.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class ShoppingBoxViewModel(
    val repository: ProductRepositoryImpl
) : ViewModel() {

    val productsResponse: MutableLiveData<List<ShoppingBoxItem>> = MutableLiveData()

    fun getShoppinBoxProduct() = viewModelScope.launch { getSafeCallShoppingBoxProduct() }

    fun updateProductQuantity(product: ShoppingBoxItem) = viewModelScope.launch {
        safeCallUpdateQuantity(product)
    }

    suspend fun safeCallUpdateQuantity(product: ShoppingBoxItem) {
        repository.updateQuantity(product.quantity, product.id)

    }

    fun deleteProductInCard(productId: Int) {
        safeCallDeleteProductInCard(productId)
    }

    fun safeCallDeleteProductInCard(productId: Int) = viewModelScope.launch {
        repository.productDao.deleteProductInShoppingBox(productId)
    }

    suspend fun getSafeCallShoppingBoxProduct() {
        try {
            val response = repository.getShoppingBoxProducts()
            productsResponse.postValue(response)

        } catch (t: Throwable) {
            Log.d("ShoppingBoxViewModel Error : ", "${t.message}")
        }
    }


}