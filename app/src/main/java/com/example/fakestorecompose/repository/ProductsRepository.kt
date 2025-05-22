package com.example.fakestorecompose.repository

import com.example.fakestorecompose.api.FakeStoreApi
import com.example.fakestorecompose.core.models.toEntity
import com.example.fakestorecompose.database.ProductDao
import com.example.fakestorecompose.database.ProductEntity
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: FakeStoreApi,
    private val productDao: ProductDao,
) {
    suspend fun getAllProducts(): List<ProductEntity> {
        try {
            val response = api.getAllProducts()
            val body = response.body()
            if (response.isSuccessful && body != null ) {
                productDao.insertProducts(body.map{ product -> product.toEntity() })
            }
        } catch (e: Exception) {}
        return productDao.getAllProducts()
    }
}