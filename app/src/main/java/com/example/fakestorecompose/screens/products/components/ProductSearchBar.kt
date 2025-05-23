package com.example.fakestorecompose.screens.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fakestorecompose.R
import com.example.fakestorecompose.screens.UiState
import com.example.fakestorecompose.screens.products.ProductEvent
import com.example.fakestorecompose.screens.products.ProductsUiState


@Composable
fun ProductSearchBar(
    state: UiState<ProductsUiState>,
    onEvent: (ProductEvent) -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier.padding(bottom =  16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            placeholder = { Text(stringResource(id = R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            value = state.let { if (state is UiState.Success) state.data.queryString else "" },
            onValueChange = { query -> onEvent(ProductEvent.UpdateSearchQuery(query)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ),
            onClick = { onEvent(ProductEvent.ToggleSortByPrice) },
        ) {
            Icon(
                painter = painterResource(id = state.let {
                    if (state is UiState.Success && state.data.sortByPrice)
                        R.drawable.sort else R.drawable.sort_disabled
                }),
                contentDescription = "Sort by price.",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}