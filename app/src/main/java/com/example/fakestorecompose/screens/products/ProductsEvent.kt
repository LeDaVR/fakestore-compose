package com.example.fakestorecompose.screens.products

sealed class ProductEvent {
    data object FetchProducts : ProductEvent()
    data class UpdateSearchQuery(val query: String) : ProductEvent()
    data object ToggleSortByPrice : ProductEvent()
    data class NavigateToDetails(val id: Int) : ProductEvent()
}