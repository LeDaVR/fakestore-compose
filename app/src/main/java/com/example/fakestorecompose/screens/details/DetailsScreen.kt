package com.example.fakestorecompose.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.fakestorecompose.navigation.Routes

@Composable
fun DetailsScreenContent(
    navController: NavHostController,
    id: Int,
    modifier: Modifier = Modifier,
) {
    Column (modifier = modifier) {
        Text("Details Screen $id")
        Button(
            onClick = { navController.navigate(Routes.Products) }
        ){
            Text(text = "Go to Products")
        }
    }
}