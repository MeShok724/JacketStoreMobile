package com.example.jacketstoremobile.ui.Views.Elements

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun Menu(navController: NavController){
    val categoriesList = listOf(
        "Catalog",
        "Favoruites",
        "Profile",
        "Sign Out"
    )

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
                    items(categoriesList) { item ->
                        Column(modifier = Modifier.fillMaxWidth().clickable{ onMenuClick(item, categoriesList, navController) }, horizontalAlignment = Alignment.CenterHorizontally) {
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
}

private fun onMenuClick(item: String, menuList: List<String>, navController: NavController) {
    when (item) {
        menuList[0] -> {} //todo
        menuList[1] -> {} //todo
        menuList[2] -> {} //todo
        menuList[3] -> unAuthorize(navController)
    }
}

private fun unAuthorize(navController: NavController){
    val auth = Firebase.auth
    auth.signOut()
    navController.navigate("login")
}