package com.example.myrecipeapp.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.myrecipeapp.ui.screens.categories.CategoriesScreen
import com.example.myrecipeapp.ui.screens.category.CategoryScreen
import com.example.myrecipeapp.ui.screens.home.HomeScreen
import com.example.myrecipeapp.ui.screens.login.LoginScreen
import com.example.myrecipeapp.ui.screens.profile.ProfileScreen
import com.example.myrecipeapp.ui.screens.recipe.RecipeScreen
import com.example.myrecipeapp.ui.screens.saved.SavedScreen
import com.example.myrecipeapp.ui.screens.search.SearchScreen
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang


@Composable
fun NavigationGraph(
    navController: NavHostController){
    NavHost(
        navController = navController,

        //startDestination = AppRoute.StartScreen.route)
        startDestination = AppRoute.HomeScreen.route){
        //startDestination = AppRoute.HomeScreen.route) {
        composable(AppRoute.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(AppRoute.SearchScreen.route) {
            SearchScreen(navController)

        }
        composable(AppRoute.SavedScreen.route) {
            SavedScreen(navController)
        }
        composable(AppRoute.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(AppRoute.LoginScreen.route) {
            LoginScreen()
        }
//        composable(AppRoute.RegistrationScreen.route) {
//            RegistrationScreen(navController)
//        }

//        composable(AppRoute.CategoryScreen.route) {
//            CategoryScreen(navController)
//        }
        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryScreen(navController = navController, categoryName = categoryName)
        }
        composable(AppRoute.CategoriesScreen.route) {
            CategoriesScreen(navController)
        }
        composable(
            route = "recipe/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeScreen(navController = navController, recipeId = recipeId)
        }
//        composable(AppRoute.RecipeScreen.route) {
//            RecipeScreen(navController)
//        }



}

//@Composable
//fun NavigationGraph(
    //navController: NavHostController = rememberNavController(),
    //navController: NavHostController,
    //dataStoreManager: DataStoreManager,
    //textSize: MutableState<Int>,
    //bgColor: MutableState<ULong>,
    //recipeFullViewModel: RecipeFullViewModel,
    //recipeCategoryViewModel: RecipeCategoryViewModel,
    //recipeByCategoryViewModel: RecipeByCategoryViewModel,
    //recipeDetailsViewModel: RecipeDetailsViewModel,
    //recipeSavedViewModel: SavedRecipeViewModel,
    //userViewModel: UserViewModel,
    //loginViewModel: LoginViewModel = viewModel(),
   // searchViewModel: SearchViewModel = viewModel()
//) {
    //NavHost(
    //navController = navController,
    //startDestination = AppRoute.StartScreen.route) {
//        composable(AppRoute.HomeScreen.route) {
//            HomeScreen(navController,recipeCategoryViewModel.recipeCategoryUiState,textSize, bgColor, recipeCategoryViewModel)
//        }
//        composable(AppRoute.SearchScreen.route) {
//            SearchScreen(navController, recipeFullViewModel.recipeFullUiState,textSize,bgColor, searchViewModel, recipeFullViewModel,recipeDetailsViewModel)
//
//        }
//        composable(AppRoute.SavedScreen.route) {
//            SavedScreen(navController,recipeSavedViewModel.recipeSavedUiState,recipeDetailsViewModel)
//        }
//
//        composable(AppRoute.ProfileScreen.route ) {
//            ProfileScreen(dataStoreManager,textSize,bgColor,userViewModel)
//            //ProfileScreen()
//        }
//        composable(AppRoute.LoginScreen.route) {
//            LoginScreen(navController, userViewModel,loginViewModel )
//        }
//        composable(AppRoute.RegistrationScreen.route) {
//            RegistrationScreen(navController, userViewModel )
//        }
//        composable(AppRoute.CategoryScreen.route) {
//            CategoryScreen(navController,recipeByCategoryViewModel.recipeByCategoryUiState,recipeByCategoryViewModel,recipeCategoryViewModel,recipeDetailsViewModel)
//        }
//
//        composable(AppRoute.RecipeScreen.route) {
//            RecipeScreen(navController,recipeDetailsViewModel.recipeDetailsUiState,recipeDetailsViewModel,recipeByCategoryViewModel,recipeSavedViewModel)
//        }
//        composable(AppRoute.StartScreen.route) {
//            StartScreen(navController)
//        }
    // }
//}
}
@Composable
fun BottomBar(
        navController: NavHostController,
        //state: MutableState<Boolean>,
        modifier: Modifier = Modifier
    ) {
    val screens = listOf(
        AppRoute.HomeScreen,
        AppRoute.SearchScreen,
        AppRoute.SavedScreen,
        AppRoute.ProfileScreen
    )

    NavigationBar(
        //modifier = modifier.height(44.dp),
        containerColor = Color.White

    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                modifier = Modifier
                    .height(44.dp)
                    //.padding(5.dp)
                    .testTag(screen.route + "_compact"),

                label = {
                    Text(
                        text = screen.title,
                        fontSize = 12.sp
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.iconId),
                        contentDescription = "",
                        //modifier = Modifier.height(22.dp)
                    )
                },

                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.LightGray,
                    selectedTextColor = MyPrimeryOrang,
                    selectedIconColor = MyPrimeryOrang,
                    unselectedIconColor = Color.LightGray,
                    indicatorColor = Color.White
                ),
            )
        }
    }

}
