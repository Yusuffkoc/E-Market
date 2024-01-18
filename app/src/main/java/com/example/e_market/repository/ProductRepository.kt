package com.example.e_market.repository

import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import retrofit2.Response

interface ProductRepository {

    suspend fun insertProducts(product: ProductResponseItem)
    suspend fun deleteProductInFavourites(product: ProductResponseItem)
    suspend fun getFavouritedProducts(): List<ProductResponseItem>
    suspend fun updateQuantity(quantity: Int, productId: Int)
    suspend fun getProducts(): Response<ProductResponse>

    suspend fun saveShoppingBoxItem(product: ShoppingBoxItem)
    suspend fun deleteShoppinBoxItem(product: Int)
    suspend fun getShoppingBoxProducts(): List<ShoppingBoxItem>
    suspend fun getInShoppingBoxSpecificProduct(productId: Int): ShoppingBoxItem

}