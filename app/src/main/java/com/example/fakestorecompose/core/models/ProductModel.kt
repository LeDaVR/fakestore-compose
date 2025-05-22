package com.example.fakestorecompose.core.models

import com.example.fakestorecompose.database.ProductEntity

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

fun ProductModel.toEntity() : ProductEntity = ProductEntity(
    id = id,
    categoryName = category.name,
    title = title,
    slug = slug,
    price = price,
    description = description,
    images = images,
)