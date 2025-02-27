package com.example.jacketstoremobile.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.jacketstoremobile.models.MyUserData
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel: ViewModel() {
    private val _userData = MutableStateFlow<MyUserData>(MyUserData())
    val userData: StateFlow<MyUserData> = _userData
    private val user = Firebase.auth.currentUser
        ?: throw Exception("Пользователь не найден")

    init{
        Firebase.firestore.collection("users").document(user.uid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val res = task.result.toObject(MyUserData::class.java)
                    if (res is MyUserData)
                    _userData.value = res
                }
            }
    }

    fun deleteAcc(navController: NavController, password: String){
        val uid = Firebase.auth.uid.toString()
        val user = Firebase.auth.currentUser ?: return
        val email = user.email ?: ""

        val credential = EmailAuthProvider.getCredential(email, password)
        user.reauthenticate(credential)
            .addOnSuccessListener {
                Firebase.firestore.collection("users").document(uid).delete()
                    .addOnCompleteListener{
                        user.delete()
                            .addOnCompleteListener{
                                Firebase.auth.signOut()
                                navController.navigate("registration")
                            }
                    }
            }
    }
}