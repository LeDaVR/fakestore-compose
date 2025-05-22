package com.example.fakestorecompose.screens.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestorecompose.core.models.ProductModel
import com.example.fakestorecompose.repository.ProductsRepository
import com.example.fakestorecompose.screens.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<ProductsUiState>>(UiState.Loading)
    val state: StateFlow<UiState<ProductsUiState>> = _uiState.asStateFlow()

    fun fetchProducts() {
        viewModelScope.launch {
            val products = repository.getAllProducts()
            _uiState.value = UiState.Success(
                data = ProductsUiState(products = products)
            )
        }
    }
}