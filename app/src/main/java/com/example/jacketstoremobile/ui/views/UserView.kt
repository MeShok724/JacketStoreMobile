package com.example.jacketstoremobile.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jacketstoremobile.models.MyUserData
import com.example.jacketstoremobile.ui.views.elements.Menu
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun UserView(navController: NavController) {
    val userData = remember { mutableStateOf(MyUserData()) }
    val userId = Firebase.auth.currentUser?.uid ?: throw Exception("Uid не найден")
    Firebase.firestore.collection("users").document(userId).get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val res = task.result.toObject(MyUserData::class.java)
                    ?: throw Exception("Информация о пользователе не найдена")
                userData.value = res
            } else {
                throw Exception("Информация о пользователе не найдена")
            }
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Text(text = "Имя: ${userData.value.name}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Имя: ${userData.value.id}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Email: ${userData.value.email}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Телефон: ${userData.value.phone}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Возраст: ${userData.value.age}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Описание: ${userData.value.description}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Размер: ${userData.value.size}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Товаров в избранном: ${userData.value.favorites.size}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Дата регистрации: ${userData.value.regDate}")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Адрес: ${userData.value.address}")
        }
    }
    Menu(navController)
}