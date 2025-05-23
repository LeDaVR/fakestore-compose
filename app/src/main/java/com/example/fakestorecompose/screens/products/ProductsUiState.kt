package com.example.fakestorecompose.screens.products

import com.example.fakestorecompose.database.ProductEntity

class ProductsUiState (
    val products: List<ProductEntity> = emptyList(),
    val queryString: String = "",
    val sortByPrice: Boolean = false,
    val isLocal: Boolean = false,
)

class ProductsResponse (
    val products: List<ProductEntity>,
    val isLocal: Boolean,
)
