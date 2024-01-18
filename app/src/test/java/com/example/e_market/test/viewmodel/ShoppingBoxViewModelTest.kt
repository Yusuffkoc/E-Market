package com.example.e_market.test.viewmodel

import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.ShoppingBoxViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ShoppingBoxViewModelTest {

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()


    @Mock
    private lateinit var repository: ProductRepositoryImpl

    private lateinit var viewModel: ShoppingBoxViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = ShoppingBoxViewModel(repository)
    }

    @Test
    fun `getShoppinBoxProduct calls getSafeCallShoppingBoxProduct in viewModelScope`() =
        testCoroutineRule.testCoroutineScope.runBlockingTest {
            viewModel.getShoppinBoxProduct()
            verify(repository, times(1)).getShoppingBoxProducts()
        }

    @Test
    fun `updateProductQuantity calls safeCallUpdateQuantity in viewModelScope`() =
        testCoroutineRule.testCoroutineScope.runBlockingTest {
            val product = mock(ShoppingBoxItem::class.java)
            viewModel.updateProductQuantity(product)
            verify(repository, times(1)).updateQuantity(product.quantity, product.id)
        }

    @Test
    fun `deleteProductInCard calls safeCallDeleteProductInCard in viewModelScope`() =
        testCoroutineRule.testCoroutineScope.runTest {
            val productId = 1
            viewModel.deleteProductInCard(productId)
            verify(repository, times(1)).productDao.deleteProductInShoppingBox(productId)
        }

    @Test
    fun `getSafeCallShoppingBoxProduct updates productsResponse with response from repository`() =
        testCoroutineRule.testCoroutineScope.runBlockingTest {
            val expectedResponse = listOf(mock(ShoppingBoxItem::class.java))
            `when`(repository.getShoppingBoxProducts()).thenReturn(expectedResponse)
            viewModel.getShoppinBoxProduct()
            assertEquals(expectedResponse, viewModel.productsResponse.value)
        }
}