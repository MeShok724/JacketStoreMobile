package com.example.jacketstoremobile.ui.views.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jacketstoremobile.models.states.MenuState
import com.example.jacketstoremobile.ui.theme.MyGray
import com.example.jacketstoremobile.viewModels.MenuViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController, menuViewModel: MenuViewModel = viewModel()) {
    val menuState by menuViewModel.menuState.collectAsState()

    TopAppBar(
        modifier = Modifier.systemBarsPadding()
            .fillMaxWidth(),
        title = { Text(text = "Меню", fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MyGray,
            titleContentColor = Color.Black,
        ),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                menuViewModel.menuList.forEach { item ->
                    Text(
                        text = item,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable { menuViewModel.onMenuClick(item) }
                            .padding(8.dp)
                    )
                }
            }
        }
    )

    when (menuState) {
        is MenuState.Idle -> {}
        is MenuState.Catalog -> {
            navController.navigate("catalog")
        }

        is MenuState.Favorites -> {
            navController.navigate("favorites")
        }

        is MenuState.Profile -> {
            navController.navigate("user")
        }

        is MenuState.SignOut -> {
            Firebase.auth.signOut()
            navController.navigate("login")
        }
    }
}