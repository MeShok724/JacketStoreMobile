package com.example.jacketstoremobile.ui.views.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jacketstoremobile.models.states.MenuState
import com.example.jacketstoremobile.viewModels.MenuViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Menu(navController: NavController, menuViewModel: MenuViewModel = viewModel()){

    val menuState by
    menuViewModel.menuState.collectAsState()

    ModalNavigationDrawer(
        modifier = Modifier.fillMaxWidth(0.7f)
            .systemBarsPadding(),
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Spacer(modifier = Modifier.height(100.dp))
                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.DarkGray))
                LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    items(menuViewModel.menuList) { item ->
                        Column(modifier = Modifier.fillMaxWidth().clickable{ menuViewModel.onMenuClick(item) }, horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(
                                text = item,
                                fontSize = 20.sp,
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.DarkGray))
                        }
                    }
                }
            }
        }
    ) {
    }

    when (menuState){
        is MenuState.Idle -> {}//todo
        is MenuState.Catalog -> {navController.navigate("main")}
        is MenuState.Favorites -> {}//todo
        is MenuState.Profile -> {}//todo
        is MenuState.SignOut -> {
            Firebase.auth.signOut()
            navController.navigate("login")
        }
    }
}