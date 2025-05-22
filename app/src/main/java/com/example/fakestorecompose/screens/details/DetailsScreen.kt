package com.example.fakestorecompose.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
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
fun DetailsScreenContent(
    viewModel: DetailsViewModel = hiltViewModel(),
    navController: NavHostController,
    id: Int,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProduct(id)
    }

    when(state) {
        is UiState.Loading -> {}
        is UiState.Error -> {}
        is UiState.Success -> {
            Column (modifier = modifier) {
                Text("${(state as UiState.Success).data.product.title}")
                Button(
                    onClick = { navController.navigate(Routes.Products) }
                ){
                    Text(text = "Go to Products")
                }
            }
        }
    }
}