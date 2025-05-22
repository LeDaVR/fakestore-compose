package com.example.fakestorecompose.repository

import com.example.fakestorecompose.api.FakeStoreApi
import com.example.fakestorecompose.core.models.ProductModel
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: FakeStoreApi,
) {
    suspend fun getAllProducts(): List<ProductModel> {
        val  response = api.getAllProducts()
        return response.body() ?: emptyList()
    }
}