package com.example.jacketstoremobile.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jacketstoremobile.models.states.CatalogState
import com.example.jacketstoremobile.models.states.JacketState
import com.example.jacketstoremobile.ui.views.elements.Menu
import com.example.jacketstoremobile.ui.views.elements.MyButton
import com.example.jacketstoremobile.ui.views.elements.MySubButton
import com.example.jacketstoremobile.viewModels.JacketViewModel

@Composable
fun JacketView(
    navController: NavController,
    jacketId: String,
    jacketViewModel: JacketViewModel = viewModel()
) {
    val jacketState by jacketViewModel.jacketState.collectAsState()
    val jacket by jacketViewModel.jacket.collectAsState()
    val isFavorite by jacketViewModel.isFavorite.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (jacket.id.isNotEmpty() && jacketState == JacketState.Idle) {
                    Spacer(modifier = Modifier.height(20.dp))
                    AsyncImage(
                        model = jacket.imageUrls[0],
                        contentDescription = "Jacket Image",
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(20.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = jacket.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 20.sp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = jacket.description,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 10.dp),
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Характеристики",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Сезон: ${jacket.season}",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Тип: ${jacket.type}",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    if (isFavorite)
                        MySubButton("Удалить из избранного") { jacketViewModel.delFromFavorites() }
                    else
                        MyButton("Добавить в избранное") { jacketViewModel.addToFavorites() }
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Фото",
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 15.sp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(0.dp, 300.dp)
                    ) {
                        items(jacket.imageUrls.filter { it.isNotEmpty() }) { imageUrl ->
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Jacket Image",
                                modifier = Modifier
                                    .fillMaxWidth(0.8f)
                                    .padding(20.dp)

                            )
                        }
                    }
                }
                when (jacketState){
                    is JacketState.Error -> { Text(text = "Error: ${(jacketState as JacketState.Error).message}", color = Color.Red) }
                    is JacketState.Idle -> {}
                    is JacketState.Loading -> { CircularProgressIndicator() }
                }
            }
        }

    }

    Menu(navController)
}