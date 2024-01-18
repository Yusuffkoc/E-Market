package com.example.e_market.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.e_market.MyApplication
import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class HomeViewModel(
    app: Application,
    val repository: ProductRepositoryImpl
) : AndroidViewModel(app) {
    // I used AndroidViewmodel for access and application and I can check network connection

    val productsResponse: MutableLiveData<Resource<List<ProductResponseItem>>> = MutableLiveData()
    val productsInShoppingBox: MutableLiveData<List<ShoppingBoxItem>> = MutableLiveData()
    val allFavouritedProducts: MutableLiveData<List<ProductResponseItem>> = MutableLiveData()

    fun getProducts() = viewModelScope.launch {
        safeGetProductsCall()
    }

    private suspend fun safeGetProductsCall() {
        try {
            productsResponse.postValue(Resource.Loading())
            if (hasInternetConnection()) {
                val response = repository.getProducts()
                productsResponse.postValue(handleMoviesResponse(response))
            } else {
                productsResponse.postValue(Resource.Error("No Internet Connection"))
            }

        } catch (t: Throwable) {
            when (t) {
                is IOException -> productsResponse.postValue(Resource.Error("Network Failure"))
                else -> productsResponse.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    fun getAllFavouritedProject(){
        safeCallgetAllFavouritedProject()
    }

    private fun safeCallgetAllFavouritedProject() = viewModelScope.launch {

        try {
            val response = repository.getFavouritedProducts()
            allFavouritedProducts.postValue(response)

        } catch (t: Throwable) {
            Log.d("SafeCallgetAllFavouritedProject Error : ", "${t.message}")
        }
    }

    fun saveFavouriteProduct(product: ProductResponseItem) = viewModelScope.launch {
        saveSafeCallFavouriteProduct(product)
    }

    fun deleteFavourite(product: ProductResponseItem) = viewModelScope.launch {
        deleteFavouriteProduct(product)
    }

    private suspend fun deleteFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.deleteProductInFavourites(product)
    }

    private suspend fun saveSafeCallFavouriteProduct(product: ProductResponseItem) {
        repository.productDao.insertFavouritedProduct(product)

    }

    fun addToCard(isIncludeInDatabase: Boolean, product: ProductResponseItem,quantity: Int) {
        if (isIncludeInDatabase) {
            updateProductQuantity(mappingProduct(product, quantity))
        } else {
            saveShoppingBoxProduct(mappingProduct(product, quantity))
        }

    }

    fun updateProductQuantity(product: ShoppingBoxItem) = viewModelScope.launch {
        safeCallUpdateQuantity(product)
    }

    private suspend fun safeCallUpdateQuantity(product: ShoppingBoxItem) {
        repository.updateQuantity(product.quantity, product.id)

    }

    fun saveShoppingBoxProduct(product: ShoppingBoxItem) =
        viewModelScope.launch {
            saveSafeCallShoppingBoxProduct(product)
        }

    private suspend fun saveSafeCallShoppingBoxProduct(product: ShoppingBoxItem) {
        repository.saveShoppingBoxItem(product)
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

    private fun handleMoviesResponse(response: Response<ProductResponse>): Resource<List<ProductResponseItem>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getShoppinBoxProduct() = viewModelScope.launch { getSafeCallShoppingBoxProduct() }

    private suspend fun getSafeCallShoppingBoxProduct() {
        try {
            val response = repository.getShoppingBoxProducts()
            productsInShoppingBox.postValue(response)

        } catch (t: Throwable) {
            Log.d("GetSafeCallShoppingBoxProduct Error : ", "${t.message}")
        }
    }


    fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}