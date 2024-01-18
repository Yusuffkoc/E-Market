package com.example.e_market.test.viewmodel

import android.app.Application
import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.HomeViewModel
import com.example.e_market.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.IOException

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var mockProductRepository: ProductRepositoryImpl

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var repository: ProductRepositoryImpl

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Mockito.`when`(mockApplication.applicationContext).thenReturn(mockApplication)
        mockProductRepository = Mockito.mock(ProductRepositoryImpl::class.java)
        homeViewModel = HomeViewModel(mockApplication, mockProductRepository)
    }

    @Test
    fun `test getProducts when internet is available`() = testCoroutineRule.testCoroutineScope.runBlockingTest {
        homeViewModel.getProducts()
        Mockito.verify(repository, Mockito.times(1)).getProducts()
    }
    @Test
    fun `test getFavouritedProducts`() = testCoroutineRule.testCoroutineScope.runBlockingTest {
        homeViewModel.getAllFavouritedProject()
        Mockito.verify(repository, Mockito.times(1)).getFavouritedProducts()
    }

    @Test
    fun `updateProductQuantity calls safeCallUpdateQuantity in viewModelScope`() =
        testCoroutineRule.testCoroutineScope.runBlockingTest {
            var product = Mockito.mock(ShoppingBoxItem::class.java)
            product.quantity = 1
            product.id = 1
            homeViewModel.updateProductQuantity(product)
            Mockito.verify(repository, Mockito.times(1)).updateQuantity(product.quantity, product.id)
        }

}