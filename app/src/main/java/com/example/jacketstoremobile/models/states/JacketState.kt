package com.example.jacketstoremobile.models.states

sealed class JacketState {
    data object Idle: JacketState()
    data object Loading: JacketState()
    data class Error(val message: String): JacketState()
}