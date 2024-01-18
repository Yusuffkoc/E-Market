package com.example.e_market.test.model

import com.example.e_market.models.ProductResponseItem
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ProductResponseItemTest {

    @Test
    fun createProduct_shouldReturnCorrectValues() {
        val productId = 1
        val brand = "BrandName"
        val createdAt = "2022-01-01"
        val description = "Product Description"
        val image = "product_image.jpg"
        val model = "Product Model"
        val name = "Product Name"
        val price = "100.00"
        val isFavorited = true

        val product = ProductResponseItem(
            id = productId,
            brand = brand,
            createdAt = createdAt,
            description = description,
            image = image,
            model = model,
            name = name,
            price = price,
            isFavorited = isFavorited
        )

        assertEquals(productId, product.id)
        assertEquals(brand, product.brand)
        assertEquals(createdAt, product.createdAt)
        assertEquals(description, product.description)
        assertEquals(image, product.image)
        assertEquals(model, product.model)
        assertEquals(name, product.name)
        assertEquals(price, product.price)
        assertEquals(isFavorited, product.isFavorited)
    }

    @Test
    fun updateFavoritedStatus_shouldUpdateCorrectly() {
        val initialProduct = ProductResponseItem(id = 1, isFavorited = false)
        val updatedProduct = initialProduct.copy(isFavorited = true)

        assertEquals(false, initialProduct.isFavorited)
        assertEquals(true, updatedProduct.isFavorited)
    }

    @Test
    fun copyProduct_shouldReturnNewInstance() {
        val originalProduct = ProductResponseItem(id = 1, name = "Original Product")
        val copiedProduct = originalProduct.copy(name = "Copied Product")

        assertEquals("Original Product", originalProduct.name)
        assertEquals("Copied Product", copiedProduct.name)
        assertEquals(originalProduct.id, copiedProduct.id)
        assertEquals(originalProduct.createdAt, copiedProduct.createdAt)
    }
}