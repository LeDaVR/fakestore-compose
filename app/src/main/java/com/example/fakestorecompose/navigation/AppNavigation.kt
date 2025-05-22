package com.example.fakestorecompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.fakestorecompose.screens.details.DetailsScreenContent
import com.example.fakestorecompose.screens.products.ProductScreenContent

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Graphs.Store
    ) {
        storeGraph(navController, modifier)
    }
}

private fun NavGraphBuilder.storeGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    navigation<Graphs.Store>(
        startDestination = Routes.Products,
    ) {

        composable<Routes.Products> {
            ProductScreenContent(
                navController = navController,
                modifier = modifier,
            )
        }

        composable<Routes.Details>() { backStackEntry ->
            DetailsScreenContent(
                navController = navController,
                id = backStackEntry.toRoute<Routes.Details>().id,
                modifier = modifier,
            )
        }
    }
}