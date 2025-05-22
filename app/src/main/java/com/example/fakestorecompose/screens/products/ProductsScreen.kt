package com.example.fakestorecompose.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fakestorecompose.navigation.Routes
import com.example.fakestorecompose.screens.UiState

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

    Column(modifier = modifier) {
        Text("Products Screen")
        Button(
            onClick = { navController.navigate(Routes.Details(1)) }
        ) {
            Text(text = "Go to Details")
        }

        when (state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Something went wrong")
            is UiState.Success -> {
                LazyColumn {
                    items((state as UiState.Success).data.products) {
                        product ->
                        Text(
                            text = product.title
                        )
                    }
                }
            }
        }
    }
}