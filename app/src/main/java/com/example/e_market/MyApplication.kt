package com.example.e_market

import android.app.Application
import com.example.e_market.db.ProductsDatabase
import com.example.e_market.repository.ProductRepositoryImpl

class MyApplication : Application() {
    private val database by lazy { ProductsDatabase.getDatabase(this) }
    val repository by lazy { ProductRepositoryImpl(database.getProductsDao()) }
}