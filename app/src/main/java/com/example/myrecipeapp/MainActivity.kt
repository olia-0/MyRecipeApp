package com.example.myrecipeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myrecipeapp.navigation.BottomBar
import com.example.myrecipeapp.ui.theme.MyRecipeAppTheme
import kotlin.math.abs
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.navigation.AppRoute
import com.example.myrecipeapp.navigation.NavigationGraph
import com.example.myrecipeapp.ui.screens.login.AuthViewModel
import com.example.myrecipeapp.ui.screens.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    //private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPrefs = UserPreferences(applicationContext)
        enableEdgeToEdge()
        setContent {
            MyRecipeAppTheme(darkTheme = false) {
                MyApp(userPrefs)
                //AppHomeScreen()
            }
        }
    }
}
@Composable
fun MyApp(
    userPrefs: UserPreferences,
    navController: NavHostController = rememberNavController()
) {
    var currentScreen by remember { mutableStateOf("loading") }

    AppEntry(
        userPrefs = userPrefs,
        onLoggedIn = { currentScreen = "home" },
        onLoginRequired = { currentScreen = "login" }
    )

    when (currentScreen) {
        "home" -> AppHomeScreen(navController)
        "login" -> LoginScreen(navController)// { currentScreen = "home" }
    }
}

@Composable
fun AppHomeScreen(
    navController: NavHostController
) {
    MyRecipeAppTheme(darkTheme = false) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            //topBar = ,
            bottomBar = {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                if (currentRoute != AppRoute.LoginScreen.route &&
                    //currentRoute != AppRoute.RegistrationScreen.route &&
                    currentRoute != AppRoute.StartScreen.route &&
                    currentRoute != AppRoute.RecipeScreen.route
                ) {
                    BottomBar(
                        navController = navController,
                        modifier = Modifier.height(90.dp)
                    )
                }
            }
        ) { innerPadding ->
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != null) {
                Log.d("currentRoute",currentRoute)
            }
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {

                NavigationGraph(navController = navController)
            }


        }

    }
}


@Composable
fun AppEntry(userPrefs: UserPreferences, onLoggedIn: () -> Unit, onLoginRequired: () -> Unit) {
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLoggedIn()
        } else {
            onLoginRequired()
        }
    }
}



//@Composable
//fun MyApp(
//    userPrefs: UserPreferences,
//    //navController: NavHostController = rememberNavController()
//) {
//    //val navController = rememberNavController()
//    var currentScreen by remember { mutableStateOf("login") }
//
//    AppEntry(
//        userPrefs = userPrefs,
//        onLoggedIn = { currentScreen = "home" },
//        onLoginRequired = { currentScreen = "login" }
//    )
//
//
//    when (currentScreen) {
//        "home" -> AppHomeScreen()
//        "login" -> LoginScreen() //{ currentScreen = "home" }
//    }
//}
//
//@Composable
//fun AppHomeScreen(
//    navController: NavHostController = rememberNavController()
//) {
//    MyRecipeAppTheme(darkTheme = false) {
//        Scaffold(
//            modifier = Modifier.fillMaxSize(),
//            //topBar = ,
//            bottomBar = {
//                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//                if (currentRoute != AppRoute.LoginScreen.route &&
//                    //currentRoute != AppRoute.RegistrationScreen.route &&
//                    currentRoute != AppRoute.StartScreen.route &&
//                    currentRoute != AppRoute.RecipeScreen.route
//                ) {
//                    BottomBar(
//                        navController = navController,
//                        modifier = Modifier.height(90.dp)
//                    )
//               }
//            }
//        ) { innerPadding ->
//            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//            if (currentRoute != null) {
//                Log.d("currentRoute",currentRoute)
//            }
//            Box(
//                modifier = Modifier.padding(innerPadding)
//            ) {
//
//                NavigationGraph(navController = navController)
//            }
//
//
//        }
//
//    }
//}
//
//
//@Composable
//fun AppEntry(userPrefs: UserPreferences, onLoggedIn: () -> Unit, onLoginRequired: () -> Unit) {
//    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(initial = false)
//
//    LaunchedEffect(isLoggedIn) {
//        if (isLoggedIn) {
//            onLoggedIn()
//        } else {
//            onLoginRequired()
//        }
//    }
//}




