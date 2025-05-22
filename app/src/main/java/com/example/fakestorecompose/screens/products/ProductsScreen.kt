package com.example.fakestorecompose.screens.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Column(modifier = modifier) {
        Text("Products Screen")
        Row(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                singleLine = true,
                value = state.let { if (state is UiState.Success) state.data.queryString else ""},
                onValueChange = { query -> onEvent(ProductEvent.UpdateSearchQuery(query)) }
            )
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp)
                    ),
                onClick = { onEvent(ProductEvent.ToggleSortByPrice) },
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Sort by price.",
                )
            }
        }
        when (state) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text("Something went wrong")
            is UiState.Success -> {
                LazyColumn {
                    items((state as UiState.Success).data.products) { product ->
                        Row {
                            Text(
                                text = product.title,
                                modifier = Modifier.weight(1f),
                            )
                            Button (
                                modifier = Modifier.weight(1f),
                                onClick =
                                    { onEvent(ProductEvent.NavigateToDetails(product.id)) }
                            ) {
                                Text(text = "Go to Details")
                            }
                        }
                    }
                }
            }
        }
    }
}