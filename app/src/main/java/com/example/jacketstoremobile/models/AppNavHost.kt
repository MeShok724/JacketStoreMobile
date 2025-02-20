package com.example.jacketstoremobile.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jacketstoremobile.ui.views.LoginView
import com.example.jacketstoremobile.ui.views.MainView
import com.example.jacketstoremobile.ui.views.RegistrationView

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginView(navController) }
        composable("registration") { RegistrationView(navController) }
        composable("main") { MainView(navController)}
    }
}