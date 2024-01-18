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

//    @get:Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    private lateinit var mockProductRepository: ProductRepositoryImpl
//
//    @Mock
//    private lateinit var mockApplication: Application
//
//    private lateinit var homeViewModel: HomeViewModel
//
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//
//        // Mock Application
//        Mockito.`when`(mockApplication.applicationContext).thenReturn(mockApplication)
//
//        // Mock ProductRepository
//        mockProductRepository = Mockito.mock(ProductRepositoryImpl::class.java)
//
//        // Initialize ViewModel with Mock ProductRepository and Mock Application
//        homeViewModel = HomeViewModel(mockApplication, mockProductRepository)
//    }
//
//    @Test
//    fun `test getProducts when internet is available`() = testDispatcher.runBlockingTest {
//        // Given
//        Mockito.`when`(mockProductRepository.getProducts()).thenReturn(Response.success(ProductResponse(listOf())))
//
//        // When
//        homeViewModel.getProducts()
//
//        // Then
//        assertEquals(Resource.Success(ProductResponse(listOf())), homeViewModel.productsResponse.value)
//    }
//
//    @Test
//    fun `test getProducts when internet is not available`() = testDispatcher.runBlockingTest {
//        // Given
//        Mockito.`when`(mockProductRepository.getProducts()).thenThrow(IOException())
//
//        // When
//        homeViewModel.getProducts()
//
//        // Then
//        assertEquals(Resource.Error("Network Failure"), homeViewModel.productsResponse.value)
//    }
//
//    // Add more tests for other functions as needed
}