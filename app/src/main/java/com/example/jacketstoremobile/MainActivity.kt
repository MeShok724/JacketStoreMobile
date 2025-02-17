package com.example.jacketstoremobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jacketstoremobile.data.Jacket
import com.example.jacketstoremobile.ui.theme.JacketStoreMobileTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {
                CatalogWindow()
            }
    }
}

@Composable
fun CatalogWindow() {
    val jackets = remember{
        mutableStateOf(emptyList<Jacket>())
    }
    val fs = Firebase.firestore
    fs.collection("jacket").get().addOnCompleteListener{ task ->
        if (task.isSuccessful){
            jackets.value = task.result.toObjects(Jacket::class.java)
        } else {
            task.exception
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            items(jackets.value){ jacket ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp)
                ){
                    Text(text = jacket.name,
                        modifier = Modifier.fillMaxWidth()
                            )
                }
            }
        }

    }
}