package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import com.example.jacketstoremobile.models.states.MenuState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MenuViewModel : ViewModel() {
    private val _menuState = MutableStateFlow<MenuState>(MenuState.Idle)
    val menuState: StateFlow<MenuState> = _menuState
    val menuList = listOf(
        "Каталог",
        "Избранное",
        "Профиль",
        "Выйти"
    )

    fun onMenuClick(item: String) {
        when (item) {
            menuList[0] -> { _menuState.value = MenuState.Catalog }
            menuList[1] -> { _menuState.value = MenuState.Favorites }
            menuList[2] -> { _menuState.value = MenuState.Profile }
            menuList[3] -> { _menuState.value = MenuState.SignOut }
        }
    }
}