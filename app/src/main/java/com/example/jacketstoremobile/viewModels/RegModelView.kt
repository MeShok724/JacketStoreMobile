package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jacketstoremobile.models.LoginState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegModelView : ViewModel() {
    private val _regState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _regState

    fun signUp(email: String, password: String){
        viewModelScope.launch{
            _regState.value = LoginState.Loading
            try {
                val result = Firebase.auth.createUserWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    _regState.value = LoginState.Success
                } else {
                    _regState.value = LoginState.Error("Registration failure")
                }
            } catch (e: Exception) {
                _regState.value = LoginState.Error(e.message ?: "Unknown error")
            }
        }
    }
}