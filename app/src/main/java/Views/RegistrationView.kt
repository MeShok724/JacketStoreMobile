package Views

import Views.Elements.MyButton
import Views.Elements.MyInputField
import Views.Elements.MySubButton
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegistrationView() {
    val auth = Firebase.auth

    val emailState = remember {
        mutableStateOf("")
    }
    val passwordState = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .systemBarsPadding()
            .padding(start = 40.dp, end = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        MyInputField(emailState.value, "Your email") {
            emailState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyInputField(passwordState.value, "Your password") {
            passwordState.value = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        MyButton("Sign Up") {signUp(auth, emailState.value, passwordState.value)}
        Spacer(modifier = Modifier.height(10.dp))
        MySubButton("Sign In") {}
    }
    Spacer(modifier = Modifier.height(10.dp))
}

private fun signUp(auth: FirebaseAuth, email: String, password: String){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("MyLog", "Sign Up successful")

            }
            else
                Log.d("MyLog", "Sign Up failure")
        }
}