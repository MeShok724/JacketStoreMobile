package com.example.jacketstoremobile.models.states

sealed class CatalogState {
    data object Idle: CatalogState()
    data object Loading: CatalogState()
    data class ItemClick(val id: String): CatalogState()
    data object Filter: CatalogState()
    data object Sorting: CatalogState()
    data object Search: CatalogState()
    data class Error(val message: String): CatalogState()
}