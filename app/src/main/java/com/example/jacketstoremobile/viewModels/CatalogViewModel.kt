package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import com.example.jacketstoremobile.models.Jacket
import com.example.jacketstoremobile.models.states.CatalogState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CatalogViewModel: ViewModel() {
    private val _catState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val catState: StateFlow<CatalogState> = _catState

    private val _jackets = MutableStateFlow<List<Jacket>>(emptyList())
    val jackets: StateFlow<List<Jacket>> = _jackets

    init {
        loadJackets()
    }

    private fun loadJackets(){
        _catState.value = CatalogState.Loading
        try {
            val fs = Firebase.firestore
            fs.collection("jacket").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _jackets.value = task.result.toObjects(Jacket::class.java)
                    _catState.value = CatalogState.Idle
                } else {
                    _catState.value = CatalogState.Error("Failed to load data")
                }
            }
        } catch (e: Exception){
            _catState.value = CatalogState.Error(e.message ?: "Unknown error")
        }

    }

    fun jacketClick(jacket: Jacket){
        _catState.value = CatalogState.ItemClick(jacket.id)
    }
}