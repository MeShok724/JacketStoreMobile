package com.example.jacketstoremobile.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jacketstoremobile.models.states.CatalogState
import com.example.jacketstoremobile.ui.views.elements.Menu
import com.example.jacketstoremobile.viewModels.FavViewModel

@Composable
fun FavoritesView(navController: NavController, favViewModel: FavViewModel = viewModel()) {
    val favState by favViewModel.favState.collectAsState()
    val jackets by favViewModel.jackets.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            items(jackets) { jacket ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.LightGray
                    ),
                    onClick = { favViewModel.jacketClick(jacket) }
                ) {
                    AsyncImage(
                        model = jacket.imageUrls[0],
                        contentDescription = "Jacket Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = jacket.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        when (favState) {
            is CatalogState.Idle -> {}
            is CatalogState.Loading -> {
                CircularProgressIndicator()
            }

            is CatalogState.Filter -> {}
            is CatalogState.Search -> {}
            is CatalogState.Sorting -> {}
            is CatalogState.ItemClick -> {
                navController.navigate("jacket/${(favState as CatalogState.ItemClick).id}")
            }

            is CatalogState.Error -> {
                Text(text = "Error: ${(favState as CatalogState.Error).message}", color = Color.Red)
            }
        }
    }

    Menu(navController)

}