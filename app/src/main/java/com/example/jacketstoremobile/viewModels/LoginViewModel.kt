package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jacketstoremobile.models.states.LoginState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun signIn(email: String, password: String){
        viewModelScope.launch{
            _loginState.value = LoginState.Loading
            try {
                val result = Firebase.auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Неправильный email или пароль")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Неизвестная ошибка")
            }
        }
    }

    fun checkAuth(){
        val auth = Firebase.auth
        if (auth.currentUser != null)
            _loginState.value = LoginState.Success
    }
}