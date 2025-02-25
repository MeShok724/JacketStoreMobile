package com.example.jacketstoremobile.ui.views

import com.example.jacketstoremobile.ui.views.elements.MyInputField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jacketstoremobile.models.states.LoginState
import com.example.jacketstoremobile.ui.views.elements.MyButton
import com.example.jacketstoremobile.ui.views.elements.MySubButton
import com.example.jacketstoremobile.viewModels.LoginViewModel

@Composable
fun LoginView(navController: NavController, viewModel: LoginViewModel = viewModel()) {

    val emailState = remember { mutableStateOf("")}
    val passwordState = remember { mutableStateOf("")}
    val loginState by viewModel.loginState.collectAsState()

    viewModel.checkAuth()

    Column(
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding()
            .padding(start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        MyInputField(emailState.value, "Введите email") {
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(passwordState.value, "Введите пароль") {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyButton("Sign In") { viewModel.signIn(emailState.value, passwordState.value) }
        Spacer(modifier = Modifier.height(10.dp))
        MySubButton("Sign Up") { navController.navigate("registration") }
        Spacer(modifier = Modifier.height(10.dp))

        when (loginState) {
            is LoginState.Success -> {
                navController.navigate("catalog")
            }
            is LoginState.Error -> {
                Text(text = "Error: ${(loginState as LoginState.Error).message}", color = Color.Red)
            }
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginState.Idle -> {}
        }
    }
}