package com.example.jacketstoremobile.ui.views

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.jacketstoremobile.ui.views.elements.MyButton
import com.example.jacketstoremobile.ui.views.elements.MyInputField
import com.example.jacketstoremobile.ui.views.elements.MySubButton
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
import com.example.jacketstoremobile.viewModels.RegModelView

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistrationView(navController: NavController, regModelView: RegModelView = viewModel()) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val nameState = remember { mutableStateOf("") }
    val ageState = remember { mutableStateOf("") }
    val phoneState = remember { mutableStateOf("") }
    val addressState = remember { mutableStateOf("") }
    val descriptionState = remember { mutableStateOf("") }
    val sizeState = remember { mutableStateOf("") }
    val regState by regModelView.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        MyInputField(nameState.value, "Введите имя") {
            nameState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(ageState.value, "Введите возраст") {
            ageState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(phoneState.value, "Введите телефон") {
            phoneState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(addressState.value, "Введите адрес") {
            addressState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(descriptionState.value, "Введите свое описание") {
            descriptionState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(sizeState.value, "Введите свой размер") {
            sizeState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyButton("Sign Up") {
            regModelView.signUp(
                emailState.value,
                passwordState.value,
                nameState.value,
                ageState.value,
                phoneState.value,
                addressState.value,
                descriptionState.value,
                sizeState.value
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        MySubButton("Sign In") { navController.navigate("login") }
        Spacer(modifier = Modifier.height(10.dp))

        when (regState) {
            is LoginState.Success -> {
                navController.navigate("catalog")
            }

            is LoginState.Error -> {
                Text(text = "Error: ${(regState as LoginState.Error).message}", color = Color.Red)
            }

            is LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is LoginState.Idle -> {}
        }
    }
}