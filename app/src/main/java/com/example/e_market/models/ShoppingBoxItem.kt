package com.example.e_market.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingboxitem")
data class ShoppingBoxItem(
    @PrimaryKey
    val id: Int,
    val brand: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val image: String? = null,
    val model: String? = null,
    val name: String? = null,
    val price: String? = null,
    var isFavorited: Boolean = false,
    var quantity: Int = 0
)