package com.example.jacketstoremobile.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jacketstoremobile.models.states.LoginState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RegModelView : ViewModel() {
    private val _regState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _regState

    @RequiresApi(Build.VERSION_CODES.O)
    fun signUp(email: String, password: String, name: String, age: String, phone: String,
               address: String, description: String, size: String){
        viewModelScope.launch{
            _regState.value = LoginState.Loading
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    createUserDoc(email, name, age, phone, address, description, size)
                    _regState.value = LoginState.Success
                } else {
                    _regState.value = LoginState.Error("Ошибка регистрации")
                }
            } catch (e: Exception) {
                _regState.value = LoginState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createUserDoc(email: String, name: String, age: String, phone: String,
                              address: String, description: String, size: String){
        val fs = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid ?: throw Exception("Uid не найден")
        val user = hashMapOf(
            "id" to userId,
            "name" to name,
            "email" to email,
            "phone" to phone,
            "age" to age,
            "description" to description,
            "size" to size,
            "favorites" to emptyList<String>(),
            "regDate" to LocalDateTime.now().format(DateTimeFormatter
                .ofPattern("dd.MM.yyyy")),
            "address" to address
        )

        fs.collection("users").document(userId)
            .set(user)
    }
}