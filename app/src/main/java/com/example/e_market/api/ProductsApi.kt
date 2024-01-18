package com.example.e_market.api

import com.example.e_market.models.ProductResponse
import com.example.e_market.models.ProductResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("/products")
    suspend fun getProducts(): Response<ProductResponse>
}