package com.example.fakestorecompose.screens.products.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.fakestorecompose.database.ProductEntity

@Composable
fun ProductLazyColumn(
    products: List<ProductEntity>,
    onProductClicked: (Int) -> Unit,
) {
    LazyColumn {
        items(products) { product ->
            ProductCard(
                product = product,
                onCardClicked = onProductClicked,
            )
        }
    }
}