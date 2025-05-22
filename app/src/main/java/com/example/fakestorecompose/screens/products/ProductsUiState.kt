package com.example.fakestorecompose.screens.products

import com.example.fakestorecompose.database.ProductEntity

class ProductsUiState (
    val products: List<ProductEntity> = emptyList(),
    val queryString: String = "",
    val sortByPrice: Boolean = false,
)