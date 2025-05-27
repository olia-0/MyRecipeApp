package com.example.myrecipeapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myrecipeapp.ui.screens.addrecipe.RecipeNameTextField
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate900
import org.checkerframework.checker.units.qual.A

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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF9800), // помаранчевий
                        Color.White        // білий
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(550.dp)
                    .width(320.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color.White)
                    .align(Alignment.CenterHorizontally)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = if (isLoginMode) "Увійти" else "Реєстрація",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate900
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (!isLoginMode) {
                        Text(
                            text = "Введіть ім'я",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Slate900,
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        RecipeNameTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier
                                .width(260.dp)
                                .height(60.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Gray100)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    Text(
                        text = "Введіть електронну пошту",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate900,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    RecipeNameTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Gray100)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Введіть пароль",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate900,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    RecipeNameTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier
                            .width(260.dp)
                            .height(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Gray100)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        if (isLoginMode) {
                            viewModel.signInWithEmail(email, password) { success ->
                                if (success) {
                                    //onLoginSuccess()
                                    navController.navigate(route = "home")
                                }
                            }
                        } else {
                            viewModel.signUpWithEmail(email, password, name) { success ->
                                if (success) {
                                    //onLoginSuccess()
                                    navController.navigate(route = "home")
                                }
                            }
                        }
                    },
                        colors = ButtonDefaults.buttonColors(MyPrimeryOrang),
                        modifier = Modifier
                        ) {
                        Text(
                            text = if (isLoginMode) "Увійти" else "Зареєструватися",
                            fontSize = 14.sp,
                            color = Slate900
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        onClick = { isLoginMode = !isLoginMode }
                    ) {
                        Text(
                            text = if (isLoginMode) "Немає акаунту? Зареєструватися" else "Вже є акаунт? Увійти",
                            fontSize = 14.sp,
                            color = Slate900
                        )
                    }
                }
            }
        }
    }





//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFFF9800), // помаранчевий
//                        Color.White        // білий
//                    )
//                )
//            )
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(text = if (isLoginMode) "Увійти" else "Реєстрація", fontSize = 24.sp)
//
//            if (!isLoginMode) {
//                OutlinedTextField(
//                    value = name,
//                    onValueChange = { name = it },
//                    label = { Text("Name") })
//            }
//
//            OutlinedTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = { Text("Email") })
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("Пароль") },
//                visualTransformation = PasswordVisualTransformation()
//            )
//
//            Button(onClick = {
//                if (isLoginMode) {
//                    viewModel.signInWithEmail(email, password) { success ->
//                        if (success) {
//                            //onLoginSuccess()
//                            navController.navigate(route = "home")
//                        }
//                    }
//                } else {
//                    viewModel.signUpWithEmail(email, password, name) { success ->
//                        if (success) {
//                            //onLoginSuccess()
//                            navController.navigate(route = "home")
//                        }
//                    }
//                }
//            }) {
//                Text(text = if (isLoginMode) "Увійти" else "Зареєструватися")
//            }

//            TextButton(onClick = { isLoginMode = !isLoginMode }) {
//                Text(text = if (isLoginMode) "Немає акаунту? Зареєструйся" else "Вже є акаунт? Увійти")
//            }
        //}
    //}
}
@Composable
fun GradientBackgroundScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFF9800), // помаранчевий
                        Color.White        // білий
                    )
                )
            )
    ) {
        // Додай тут вміст
        Text(
            text = "Градієнтний фон",
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}