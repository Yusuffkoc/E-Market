package com.example.e_market.test.repository

import com.example.e_market.api.RetrofitInstance
import com.example.e_market.db.ProductsDao
import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

class ProductRepositoryImplTest {

    private lateinit var productRepository: ProductRepositoryImpl
    private lateinit var productsDao: ProductsDao

    @Before
    fun setup() {
        productsDao = mock()
        productRepository = ProductRepositoryImpl(productsDao)
    }

    @Test
    fun `insertProducts calls insertFavouritedProduct in productsDao`() = runBlocking {
        val product = mock<ProductResponseItem>()
        productRepository.insertProducts(product)
        verify(productsDao, times(1)).insertFavouritedProduct(product)
    }

    @Test
    fun `deleteProductInFavourites calls deleteProductInFavourites in productsDao`() = runBlocking {
        val product = mock<ProductResponseItem>()
        productRepository.deleteProductInFavourites(product)
        verify(productsDao, times(1)).deleteProductInFavourites(product)
    }

    @Test
    fun `getFavouritedProducts calls getFavouritedProducts in productsDao`() = runBlocking {
        val expectedList = listOf(mock<ProductResponseItem>())
        whenever(productsDao.getFavouritedProducts()).thenReturn(expectedList)
        val result = productRepository.getFavouritedProducts()
        verify(productsDao, times(1)).getFavouritedProducts()
        assertEquals(expectedList, result)
    }

    @Test
    fun `updateQuantity calls updateQuantity in productsDao`() = runBlocking {
        val quantity = 5
        val productId = 1
        productRepository.updateQuantity(quantity, productId)
        verify(productsDao, times(1)).updateQuantity(quantity, productId)
    }

    @Test
    fun `getProducts calls getProducts in RetrofitInstance`() = runBlocking {
        val response = mock<Response<ProductResponse>>()
        whenever(RetrofitInstance.api.getProducts()).thenReturn(response)
        val result = productRepository.getProducts()
        verify(RetrofitInstance.api, times(1)).getProducts()
        assertEquals(response, result)
    }

    @Test
    fun `saveShoppingBoxItem calls insertShoppingBoxProduct in productsDao`() = runBlocking {
        val product = mock<ShoppingBoxItem>()
        productRepository.saveShoppingBoxItem(product)
        verify(productsDao, times(1)).insertShoppingBoxProduct(product)
    }

    @Test
    fun `deleteShoppinBoxItem calls deleteProductInShoppingBox in productsDao`() = runBlocking {
        val productId = 1
        productRepository.deleteShoppinBoxItem(productId)
        verify(productsDao, times(1)).deleteProductInShoppingBox(productId)
    }

    @Test
    fun `getShoppingBoxProducts calls getShoppingBoxProducts in productsDao`() = runBlocking {
        val expectedList = listOf(mock<ShoppingBoxItem>())
        whenever(productsDao.getShoppingBoxProducts()).thenReturn(expectedList)
        val result = productRepository.getShoppingBoxProducts()
        verify(productsDao, times(1)).getShoppingBoxProducts()
        assertEquals(expectedList, result)
    }

    @Test
    fun `getInShoppingBoxSpecificProduct calls getInShoppingBoxSpecificProduct in productsDao`() = runBlocking {
        val productId = 1
        val expectedProduct = mock<ShoppingBoxItem>()
        whenever(productsDao.getInShoppingBoxSpecificProduct(productId)).thenReturn(expectedProduct)
        val result = productRepository.getInShoppingBoxSpecificProduct(productId)
        verify(productsDao, times(1)).getInShoppingBoxSpecificProduct(productId)
        assertEquals(expectedProduct, result)
    }
}