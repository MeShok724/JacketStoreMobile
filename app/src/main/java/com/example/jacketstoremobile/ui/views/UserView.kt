package com.example.jacketstoremobile.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jacketstoremobile.ui.views.elements.MyButton
import com.example.jacketstoremobile.ui.views.elements.TopNavigationBar
import com.example.jacketstoremobile.viewModels.UserViewModel

@Composable
fun UserView(navController: NavController, userViewModel: UserViewModel = viewModel()) {
    val userData by userViewModel.userData.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val password = remember { mutableStateOf("") }


    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopNavigationBar(navController)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Имя: ${userData.name}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "UID: ${userData.id}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Email: ${userData.email}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Телефон: ${userData.phone}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Возраст: ${userData.age}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Описание: ${userData.description}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Размер: ${userData.size}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Товаров в избранном: ${userData.favorites.size}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Дата регистрации: ${userData.regDate}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Адрес: ${userData.address}")
            Spacer(modifier = Modifier.height(40.dp))
        }
        MyButton("Удалить аккаунт") {
            showDialog.value = true
        }
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text("Подтверждение") },
                text = {
                    Column {
                        Text("Введите пароль для подтверждения")
                        TextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            visualTransformation = PasswordVisualTransformation()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            userViewModel.deleteAcc(navController, password.value)
                            showDialog.value = false
                        }
                    ) {
                        Text("Удалить")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog.value = false }
                    ) {
                        Text("Отмена")
                    }
                }
            )
        }
    }
}

