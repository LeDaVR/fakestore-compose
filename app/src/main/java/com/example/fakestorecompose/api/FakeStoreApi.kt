package com.example.fakestorecompose.api

import com.example.fakestorecompose.core.models.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface FakeStoreApi {
    @GET("products")
    suspend fun getAllProducts() : Response<List<ProductModel>?>
}