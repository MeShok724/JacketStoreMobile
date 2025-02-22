package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.jacketstoremobile.models.Jacket
import com.example.jacketstoremobile.models.states.CatalogState
import com.example.jacketstoremobile.models.states.JacketState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JacketViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val _jacketState = MutableStateFlow<JacketState>(JacketState.Loading)
    val jacketState: StateFlow<JacketState> = _jacketState

    private val _jacket = MutableStateFlow<Jacket>(Jacket())
    val jacket: StateFlow<Jacket> = _jacket

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
                            jackets.firstOrNull() ?: throw IllegalStateException("Jacket not found")
                        _jacketState.value = JacketState.Idle
                    } else {
                        _jacketState.value = JacketState.Error("Failed to load data")
                    }
                }
        } catch (e: Exception) {
            _jacketState.value = JacketState.Error(e.message ?: "Unknown error")
        }
    }
}