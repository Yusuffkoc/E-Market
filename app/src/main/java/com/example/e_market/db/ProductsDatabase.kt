package com.example.e_market.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem

@Database(entities = [ShoppingBoxItem::class,ProductResponseItem::class], version = 1, exportSchema = true)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun getProductsDao(): ProductsDao

    companion object {
        @Volatile
        private var INSTANCE: ProductsDatabase? = null

        fun getDatabase(context: Context): ProductsDatabase {
            return INSTANCE ?: synchronized(this)
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsDatabase::class.java,
                    "products_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}