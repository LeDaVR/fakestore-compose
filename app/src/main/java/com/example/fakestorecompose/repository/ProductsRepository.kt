package com.example.fakestorecompose.repository

import android.util.Log
import coil3.network.HttpException
import com.example.fakestorecompose.api.FakeStoreApi
import com.example.fakestorecompose.core.models.toEntity
import com.example.fakestorecompose.database.ProductDao
import com.example.fakestorecompose.screens.products.ProductsResponse
import okio.IOException
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val api: FakeStoreApi,
    private val productDao: ProductDao,
) {
    suspend fun getAllProducts(): Result<ProductsResponse> {
        try {
            val response = api.getAllProducts()
            val body = response.body()
            if (response.isSuccessful && body != null ) {
                productDao.insertProducts(body.map{ product -> product.toEntity() })
            }
            return Result.success(
                ProductsResponse(
                    products = productDao.getAllProducts(),
                    isLocal = false,
                )
            )
        } catch (e: Exception) {
            when(e) {
                is IOException -> {
                    Log.e("Network", "no internet io")
                    return Result.success(ProductsResponse(
                        products = productDao.getAllProducts(),
                        isLocal = true,
                    ))
                }
            }
            return Result.failure(e)
        }
    }
}