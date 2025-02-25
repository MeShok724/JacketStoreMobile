package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.jacketstoremobile.models.Jacket
import com.example.jacketstoremobile.models.states.JacketState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JacketViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _jacketState = MutableStateFlow<JacketState>(JacketState.Loading)
    val jacketState: StateFlow<JacketState> = _jacketState

    private val _jacket = MutableStateFlow<Jacket>(Jacket())
    val jacket: StateFlow<Jacket> = _jacket

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    init {
        val jacketId: String? = savedStateHandle["jacketId"]
        jacketId?.let { loadJacket(it) }
    }

    private fun loadJacket(id: String) {
        _jacketState.value = JacketState.Loading
        try {
            val fs = Firebase.firestore
            fs.collection("jacket")
                .whereEqualTo("id", id)
                .limit(1)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val jackets = task.result.toObjects(Jacket::class.java)
                        _jacket.value =
                            jackets.firstOrNull() ?: throw IllegalStateException("Не удалось загрузить данные")
                        checkIfFavorite()
                        _jacketState.value = JacketState.Idle
                    } else {
                        _jacketState.value = JacketState.Error("Не удалось загрузить данные")
                    }
                }
        } catch (e: Exception) {
            _jacketState.value = JacketState.Error(e.message ?: "Неизвестная ошибка")
        }
    }

    fun addToFavorites(){
        _jacketState.value = JacketState.Loading
        val fs = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid ?: throw Exception("Uid не найден")
        fs.collection("users").document(userId)
            .update("favorites", FieldValue.arrayUnion(_jacket.value.id))
            .addOnSuccessListener {
                _isFavorite.value = true
                _jacketState.value = JacketState.Idle
            }
    }
    fun delFromFavorites(){
        _jacketState.value = JacketState.Loading
        val fs = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid ?: throw Exception("Uid не найден")
        fs.collection("users").document(userId)
            .update("favorites", FieldValue.arrayRemove(_jacket.value.id))
            .addOnSuccessListener {
                _isFavorite.value = false
                _jacketState.value = JacketState.Idle
            }
    }
    private fun checkIfFavorite() {
        val fs = Firebase.firestore
        val userId = Firebase.auth.currentUser?.uid ?: throw Exception("Uid не найден")
        fs.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val favorites = document.get("favorites") as? List<*> ?: emptyList<String>()
                _isFavorite.value = favorites.contains(_jacket.value.id)
            }
            .addOnFailureListener {
                _isFavorite.value = false
            }
    }
}