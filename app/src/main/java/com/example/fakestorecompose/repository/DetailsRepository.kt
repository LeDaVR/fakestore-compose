package com.example.fakestorecompose.repository

import com.example.fakestorecompose.database.ProductDao
import com.example.fakestorecompose.database.ProductEntity
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val productDao: ProductDao,
) {
    suspend fun getProductByID(id: Int): ProductEntity {
        val product = productDao.getProductByID(id)
        return product
    }
}