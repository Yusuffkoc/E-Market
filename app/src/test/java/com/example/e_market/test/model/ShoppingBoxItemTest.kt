package com.example.e_market.test.model

import com.example.e_market.models.ShoppingBoxItem
import junit.framework.TestCase.assertEquals
import org.junit.Test


class ShoppingBoxItemTest {

    @Test
    fun createShoppingBoxItem_shouldReturnCorrectValues() {
        val itemId = 1
        val brand = "BrandName"
        val createdAt = "2022-01-01"
        val description = "Item Description"
        val image = "item_image.jpg"
        val model = "Item Model"
        val name = "Item Name"
        val price = "50.00"
        val isFavorited = true
        val quantity = 2

        val shoppingBoxItem = ShoppingBoxItem(
            id = itemId,
            brand = brand,
            createdAt = createdAt,
            description = description,
            image = image,
            model = model,
            name = name,
            price = price,
            isFavorited = isFavorited,
            quantity = quantity
        )

        assertEquals(itemId, shoppingBoxItem.id)
        assertEquals(brand, shoppingBoxItem.brand)
        assertEquals(createdAt, shoppingBoxItem.createdAt)
        assertEquals(description, shoppingBoxItem.description)
        assertEquals(image, shoppingBoxItem.image)
        assertEquals(model, shoppingBoxItem.model)
        assertEquals(name, shoppingBoxItem.name)
        assertEquals(price, shoppingBoxItem.price)
        assertEquals(isFavorited, shoppingBoxItem.isFavorited)
        assertEquals(quantity, shoppingBoxItem.quantity)
    }

    @Test
    fun updateFavoritedStatus_shouldUpdateCorrectly() {
        val initialItem = ShoppingBoxItem(id = 1, isFavorited = false)
        val updatedItem = initialItem.copy(isFavorited = true)

        assertEquals(false, initialItem.isFavorited)
        assertEquals(true, updatedItem.isFavorited)
    }

    @Test
    fun updateQuantity_shouldUpdateCorrectly() {
        val initialItem = ShoppingBoxItem(id = 1, quantity = 2)
        val updatedItem = initialItem.copy(quantity = 5)

        assertEquals(2, initialItem.quantity)
        assertEquals(5, updatedItem.quantity)
    }

    @Test
    fun copyShoppingBoxItem_shouldReturnNewInstance() {
        val originalItem = ShoppingBoxItem(id = 1, name = "Original Item", quantity = 3)
        val copiedItem = originalItem.copy(name = "Copied Item", quantity = 1)

        assertEquals("Original Item", originalItem.name)
        assertEquals("Copied Item", copiedItem.name)
        assertEquals(3, originalItem.quantity)
        assertEquals(1, copiedItem.quantity)
        assertEquals(originalItem.id, copiedItem.id)
        assertEquals(originalItem.createdAt, copiedItem.createdAt)
    }
}