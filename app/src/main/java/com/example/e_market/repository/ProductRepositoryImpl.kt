package com.example.e_market.repository

import com.example.e_market.api.RetrofitInstance
import com.example.e_market.db.ProductsDao
import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import retrofit2.Response

class ProductRepositoryImpl(
    val productDao: ProductsDao
) : ProductRepository {
    override suspend fun insertProducts(product: ProductResponseItem) {
        productDao.insertFavouritedProduct(product = product)
    }

    override suspend fun deleteProductInFavourites(product: ProductResponseItem) {
        productDao.deleteProductInFavourites(product = product)
    }

    override suspend fun getFavouritedProducts(): List<ProductResponseItem> {
        return productDao.getFavouritedProducts()
    }

    override suspend fun updateQuantity(quantity: Int, productId: Int) {
        productDao.updateQuantity(quantity, productId)
    }


    override suspend fun getProducts(): Response<ProductResponse> {
        return RetrofitInstance.api.getProducts()
    }

    override suspend fun saveShoppingBoxItem(product: ShoppingBoxItem) {
        productDao.insertShoppingBoxProduct(product)
    }

    override suspend fun deleteShoppinBoxItem(product: Int) {
        productDao.deleteProductInShoppingBox(product)
    }

    override suspend fun getShoppingBoxProducts(): List<ShoppingBoxItem> {
        return productDao.getShoppingBoxProducts()
    }

    override suspend fun getInShoppingBoxSpecificProduct(productId: Int): ShoppingBoxItem {
        return productDao.getInShoppingBoxSpecificProduct(productId)
    }

}