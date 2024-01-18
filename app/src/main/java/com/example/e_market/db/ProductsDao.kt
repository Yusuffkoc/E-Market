package com.example.e_market.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem

@Dao
interface ProductsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouritedProduct(product: ProductResponseItem)

    @Query("SELECT * FROM products")
    suspend fun getFavouritedProducts(): List<ProductResponseItem>

    @Delete
    suspend fun deleteProductInFavourites(product: ProductResponseItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingBoxProduct(product: ShoppingBoxItem)

    @Query("DELETE FROM shoppingboxitem WHERE id = :productId")
    suspend fun deleteProductInShoppingBox(productId: Int)

    @Query("SELECT * FROM shoppingboxitem")
    suspend fun getShoppingBoxProducts():List<ShoppingBoxItem>

    @Query("SELECT * FROM shoppingboxitem WHERE id LIKE :productId")
    suspend fun getInShoppingBoxSpecificProduct(productId: Int):ShoppingBoxItem

    @Query("UPDATE shoppingboxitem SET quantity = :quantity WHERE id LIKE :productId ")
    suspend fun updateQuantity(quantity: Int, productId: Int)

    @Update
    suspend fun updateProduct(product: ShoppingBoxItem)
}