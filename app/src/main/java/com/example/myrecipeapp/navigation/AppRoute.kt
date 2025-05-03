package com.example.myrecipeapp.navigation

import com.example.myrecipeapp.R

enum class AppRoute(val route: String, val title: String, val iconId : Int) {
    StartScreen("start","Start",0),
    HomeScreen("home", "Home", R.drawable.home),
    SearchScreen("search", "Search", R.drawable.search),
    SavedScreen("saved", "Saved", R.drawable.saved),
    ProfileScreen("profile", "Profile", R.drawable.profile),
    LoginScreen("login", "Login", R.drawable.login),
    RegistrationScreen("registration","Registration", 0),
    CategoryScreen("category","Category",0),
    CategoriesScreen("categories","Categories",0),
    RecipeScreen("recipe","Recipe",0)
}