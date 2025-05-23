package com.example.fakestorecompose.screens.products.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
fun ProductLazyColumnPreview() {
    val testProducts = List(5) { index ->
        ProductEntity(
            id = index + 1,
            categoryName = when (index % 3) {
                0 -> "Electronics"
                1 -> "Clothing"
                else -> "Home"
            },
            description = "Description for product ${index + 1}",
            images = listOf("https://fakestoreapi.com/img/product${index + 1}.jpg"),
            price = 10000L * (index + 1),
            slug = "product-${index + 1}",
            title = "Product ${index + 1}"
        )
    }

    ProductLazyColumn(
        products = testProducts,
        onProductClicked = {}
    )
}