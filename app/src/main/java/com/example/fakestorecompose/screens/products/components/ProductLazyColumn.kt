package com.example.fakestorecompose.screens.products.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.fakestorecompose.database.ProductEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductLazyColumn(
    products: List<ProductEntity>,
    onProductClicked: (Int) -> Unit,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
) {
    if (products.isEmpty() && !isRefreshing) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                "No results found.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(products) { product ->
                ProductCard(
                    product = product,
                    onCardClicked = onProductClicked,
                )
            }
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
        onProductClicked = {},
        onRefresh = {},
        isRefreshing = false,
    )
}

@Preview(showBackground = true)
@Composable
fun ProductLazyColumnEmptyPreview() {
    ProductLazyColumn(
        products = emptyList(),
        onProductClicked = {},
        onRefresh = {},
        isRefreshing = false,
    )
}