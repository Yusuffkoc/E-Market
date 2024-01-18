package com.example.e_market.test.viewmodel

import com.example.e_market.models.ProductResponseItem
import com.example.e_market.models.ShoppingBoxItem
import com.example.e_market.repository.ProductRepository
import com.example.e_market.repository.ProductRepositoryImpl
import com.example.e_market.ui.viewmodel.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @Mock
    private lateinit var mockProductRepository: ProductRepository

    private lateinit var detailViewModel: DetailViewModel

    private val testDispatcher = TestCoroutineDispatcher()

//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
//
//        // Mock ProductRepository
//        mockProductRepository = Mockito.mock(ProductRepositoryImpl::class.java)
//
//        // Initialize ViewModel with Mock ProductRepository
//        detailViewModel = DetailViewModel(mockProductRepository)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    @Test
//    fun `test saveFavouriteProduct`() {
//        // Given
//        val mockProduct = ProductResponseItem(/* set your desired values */)
//
//        // When
//        detailViewModel.saveFavouriteProduct(mockProduct)
//
//        // Then
//        // Add assertions here based on the expected behavior
//        // Verify interactions with mock objects if needed
//    }
//
//    // Add more tests for other functions as needed
}