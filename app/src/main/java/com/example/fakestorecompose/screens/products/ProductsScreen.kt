package com.example.fakestorecompose.screens.products

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        ProductSearchBar(
            state = state,
            onEvent = onEvent,
            modifier = Modifier.fillMaxWidth(),
        )
        when (state) {
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

            is UiState.Success, UiState.Loading -> {
                if (state is UiState.Success && state.data.isLocal) {
                    Toast.makeText(
                        context,
                        "No connexion established.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ProductLazyColumn(
                    products = state.let { if (state is UiState.Success) state.data.products else emptyList() },
                    onProductClicked = { id -> onEvent(ProductEvent.NavigateToDetails(id)) },
                    onRefresh = { onEvent(ProductEvent.FetchProducts) },
                    isRefreshing = state is UiState.Loading,
                )
            }
        }
    }
}