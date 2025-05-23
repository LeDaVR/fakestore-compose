package com.example.fakestorecompose.screens.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakestorecompose.database.ProductEntity
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

    private var unfilteredProducts: List<ProductEntity> = emptyList()

    init {
        Log.e("Fetch","init viewmodel" )
        fetchProducts()
    }

    private fun fetchProducts() {
        Log.e("Fetch","products" )
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getAllProducts()
                .onSuccess { productsResponse: ProductsResponse ->
                    unfilteredProducts = productsResponse.products
                    _uiState.value = UiState.Success(
                        data = ProductsUiState (
                            products = productsResponse.products,
                            isLocal = productsResponse.isLocal,
                        )
                    )
                }
                .onFailure {
                    _uiState.value = UiState.Error(
                        message = "Network Error."
                    )
                }
        }
    }

    fun onEvent(event: ProductEvent) {
        when(event) {
            is ProductEvent.ToggleSortByPrice -> toggleSortByPrice()
            is ProductEvent.UpdateSearchQuery -> updateSearchQuery(event.query)
            is ProductEvent.FetchProducts -> fetchProducts()
            is ProductEvent.NavigateToDetails -> {}
        }
    }

    private fun toggleSortByPrice() {
        if (_uiState.value !is UiState.Success){
            return
        }

        val uiState = (_uiState.value as UiState.Success)

        _uiState.value = UiState.Success(
            data = ProductsUiState(
                queryString = uiState.data.queryString,
                sortByPrice = !uiState.data.sortByPrice,
                products = filterAndSortProducts(
                    unfilteredProducts,
                    uiState.data.queryString,
                    !uiState.data.sortByPrice
                )
            )
        )
    }

    private fun updateSearchQuery(query: String) {
        if (_uiState.value !is UiState.Success){
            return
        }

        val uiState = (_uiState.value as UiState.Success)

        _uiState.value = UiState.Success(
            data = ProductsUiState(
                queryString = query,
                sortByPrice = uiState.data.sortByPrice,
                products = filterAndSortProducts(
                    unfilteredProducts,
                    query,
                    uiState.data.sortByPrice
                )
            )
        )
    }

    private fun filterAndSortProducts(
        products: List<ProductEntity>,
        queryString: String,
        sortByPrice: Boolean,
    ): List<ProductEntity> {
        var result = products.toList();

        if (queryString.isNotEmpty()) {
            result = result.filter { product ->
                product.title.lowercase().contains(queryString.lowercase())
            }
        }

        if (sortByPrice) {
            result = result.sortedBy { p -> p.price }
        }
        return result
    }
}