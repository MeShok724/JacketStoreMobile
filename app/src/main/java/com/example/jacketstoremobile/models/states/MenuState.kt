package com.example.jacketstoremobile.models.states

sealed class MenuState {
    data object Idle: MenuState()
    data object Catalog: MenuState()
    data object Profile: MenuState()
    data object Favorites: MenuState()
    data object SignOut: MenuState()
}