package com.example.jacketstoremobile.ui.Views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.jacketstoremobile.ui.Views.Elements.Catalog
import com.example.jacketstoremobile.ui.Views.Elements.Menu

@Composable
fun MainView(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()){
        Catalog(navController)
        Menu(navController)
    }
}

