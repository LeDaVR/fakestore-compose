package com.example.fakestorecompose.screens.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fakestorecompose.navigation.Routes
import com.example.fakestorecompose.screens.UiState
import com.example.fakestorecompose.screens.products.components.ProductLazyColumn
import com.example.fakestorecompose.screens.products.components.ProductSearchBar

@Composable
fun ProductScreenContent(
    viewModel: ProductsViewModel = hiltViewModel(),
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts()
    }

    ProductScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is ProductEvent.NavigateToDetails -> {
                    navController.navigate(Routes.Details(event.id))
                }
                else -> viewModel.onEvent(event)
            }
        },
        modifier = modifier,
    )
}

@Composable
fun ProductScreen(
    state: UiState<ProductsUiState>,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier
        .padding(16.dp)
    ) {
        ProductSearchBar(
            state = state,
            onEvent = onEvent,
            modifier = Modifier.fillMaxWidth(),
        )
        when (state) {
            is UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(96.dp)
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 4.dp
                )
            }
            is UiState.Error -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Oops! Something went wrong",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onEvent(ProductEvent.FetchProducts) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Try Again")
                }
            }
            is UiState.Success -> ProductLazyColumn(
                products = state.data.products,
                onProductClicked = { id -> onEvent(ProductEvent.NavigateToDetails(id)) }
            )
        }
    }
}