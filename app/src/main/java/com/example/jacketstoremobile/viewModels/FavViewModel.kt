package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import com.example.jacketstoremobile.models.Jacket
import com.example.jacketstoremobile.models.states.CatalogState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavViewModel : ViewModel() {
    private val _favState = MutableStateFlow<CatalogState>(CatalogState.Loading)
    val favState: StateFlow<CatalogState> = _favState

    private val _jackets = MutableStateFlow<List<Jacket>>(emptyList())
    val jackets: StateFlow<List<Jacket>> = _jackets

    init {
        loadFavJackets()
    }

    private fun loadFavJackets() {
        _favState.value = CatalogState.Loading
        var favoritesList: List<String>
        try {
            val userId = Firebase.auth.uid ?: throw Exception("Не удалось получить uid")
            val fs = Firebase.firestore
            fs.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    favoritesList =
                        (document.get("favorites") as? List<*>)?.mapNotNull { it as? String }
                            ?: emptyList()
                    if (favoritesList.isEmpty()){
                        _favState.value = CatalogState.Idle
                        return@addOnSuccessListener
                    }
                    fs.collection("jacket").get()
                        .addOnSuccessListener { body ->
                            val allJackets = body.toObjects(Jacket::class.java)
                            _jackets.value = allJackets.filter { it.id in favoritesList }
                        }
                        .addOnFailureListener { throw Exception("Не удалось загрузить товары") }
                }
                .addOnFailureListener { throw Exception("Не удалось загрузить избранное") }
        } catch (e: Exception) {
            _favState.value = CatalogState.Error(e.message ?: "Неизвестная ошибка")
        }

    }

    fun jacketClick(jacket: Jacket) {
        _favState.value = CatalogState.ItemClick(jacket.id)
    }
}