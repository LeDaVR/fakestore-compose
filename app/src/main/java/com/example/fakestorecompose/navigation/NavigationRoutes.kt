package com.example.fakestorecompose.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Graphs {
    @Serializable data object Store: Graphs()
}

@Serializable
sealed class Routes {
    @Serializable data object Products : Routes()
    @Serializable data class Details(val id: Int) : Routes()
}