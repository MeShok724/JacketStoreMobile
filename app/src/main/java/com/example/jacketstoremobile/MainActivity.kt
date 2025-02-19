package com.example.jacketstoremobile

import Views.CatalogView
import Views.MainView
import Views.RegistrationView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatalogView()
        }
    }
}