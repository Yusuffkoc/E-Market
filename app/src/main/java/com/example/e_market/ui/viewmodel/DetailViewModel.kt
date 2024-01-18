package com.example.e_market.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import kotlinx.coroutines.launch

class DetailViewModel(
    val repository: ProductRepositoryImpl
) : ViewModel() {

    val specificProductsInShoppingBox: MutableLiveData<ShoppingBoxItem> = MutableLiveData()


    fun saveFavouriteProduct(product: ProductResponseItem) = viewModelScope.launch {
        saveSafeCallFavouriteProduct(product)
    }

    fun deleteFavourite(product: ProductResponseItem) = viewModelScope.launch {
        deleteFavouriteProduct(product)
    }

    fun saveProductToDatabase(isIncluded: Boolean, product: ProductResponseItem,quantity: Int) {
        if (isIncluded) {
            updateProductQuantity(mappingProduct(product, quantity))
        } else {
           saveShoppingBoxProduct(mappingProduct(product, quantity))
        }
    }

    fun saveShoppingBoxProduct(product: ShoppingBoxItem) =
        viewModelScope.launch {
            saveSafeCallShoppingBoxProduct(product)
        }

    private fun mappingProduct(product: ProductResponseItem, quantity: Int): ShoppingBoxItem {
        return ShoppingBoxItem(
            id = product.id,
            brand = product.brand,
            createdAt = product.createdAt,
            description = product.description,
            image = product.image,
            model = product.model,
            name = product.name,
            price = product.price,
            isFavorited = product.isFavorited,
            quantity = quantity
        )
    }



    private suspend fun saveSafeCallShoppingBoxProduct(product: ShoppingBoxItem) {
        repository.saveShoppingBoxItem(product)
    }

    private suspend fun deleteFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.deleteProductInFavourites(product)
    }

    private suspend fun saveSafeCallFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.insertFavouritedProduct(product)
    }

    fun getSpecificProductsInShoppingBox(productId: Int) {
        viewModelScope.launch { safeCallGetSpecificProductsInShoppingBox(productId) }
    }

    private suspend fun safeCallGetSpecificProductsInShoppingBox(productId: Int) {

        try {
            val response = repository.getInShoppingBoxSpecificProduct(productId)
            specificProductsInShoppingBox.postValue(response)

        } catch (t: Throwable) {
            Log.d("ShoppingBoxViewModel Error : ", "${t.message}")
        }

    }

    fun updateProductQuantity(product: ShoppingBoxItem) = viewModelScope.launch {
        safeCallUpdateQuantity(product)
    }

    private suspend fun safeCallUpdateQuantity(product: ShoppingBoxItem) {
        repository.updateQuantity(product.quantity, product.id)

    }

}