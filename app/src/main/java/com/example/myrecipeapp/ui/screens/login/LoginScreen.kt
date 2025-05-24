package com.example.myrecipeapp.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    navController: NavHostController,
    //onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = if (isLoginMode) "Увійти" else "Реєстрація", fontSize = 24.sp)

        if(!isLoginMode){
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        }

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Пароль") }, visualTransformation = PasswordVisualTransformation())

        Button(onClick = {
            if (isLoginMode) {
                viewModel.signInWithEmail(email, password) { success ->
                    if (success){
                    //onLoginSuccess()
                        navController.navigate(route = "home")
                    }
                }
            } else {
                viewModel.signUpWithEmail(email, password,name) { success ->
                    if (success){
                    //onLoginSuccess()
                        navController.navigate(route = "home")
                    }
                }
            }
        }) {
            Text(text = if (isLoginMode) "Увійти" else "Зареєструватися")
        }

        TextButton(onClick = { isLoginMode = !isLoginMode }) {
            Text(text = if (isLoginMode) "Немає акаунту? Зареєструйся" else "Вже є акаунт? Увійти")
        }
    }
}
