package com.example.fakestorecompose.screens.details

sealed class DetailsEvent {
    data object NavigateToProducts : DetailsEvent()
}