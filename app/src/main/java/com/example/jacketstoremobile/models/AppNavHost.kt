package com.example.jacketstoremobile.models

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jacketstoremobile.ui.Views.LoginView
import com.example.jacketstoremobile.ui.Views.MainView
import com.example.jacketstoremobile.ui.Views.RegistrationView

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginView(navController) }
        composable("registration") { RegistrationView(navController) }
        composable("main") { MainView(navController)}
    }
}