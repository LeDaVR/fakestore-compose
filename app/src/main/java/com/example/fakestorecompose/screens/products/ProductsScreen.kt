package com.example.fakestorecompose.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fakestorecompose.navigation.Routes

@Composable
fun ProductScreenContent(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        Text("Products Screen")
        Button(
            onClick = { navController.navigate(Routes.Details(1)) }
        ){
            Text(text = "Go to Details")
        }
    }
}