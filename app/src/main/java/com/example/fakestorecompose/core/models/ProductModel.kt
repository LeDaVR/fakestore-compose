package com.example.fakestorecompose.core.models

data class ProductModel(
    val category: Category,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Long,
    val slug: String,
    val title: String
)

data class Category(
    val id: Int,
    val image: String,
    val name: String,
    val slug: String
)