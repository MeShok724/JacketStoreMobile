package com.example.jacketstoremobile.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.jacketstoremobile.ui.views.LoginView
import com.example.jacketstoremobile.ui.views.CatalogView
import com.example.jacketstoremobile.ui.views.JacketView
import com.example.jacketstoremobile.ui.views.RegistrationView

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginView(navController) }
        composable("registration") { RegistrationView(navController) }
        composable("catalog") { CatalogView(navController)}
        composable(
            route = "jacket/{jacketId}",
            arguments = listOf(navArgument("jacketId"){type = NavType.StringType}))
        { backStackEntry ->
            val jacketId = backStackEntry.arguments?.getString("jacketId") ?: ""
            JacketView(navController, jacketId)}
    }
}