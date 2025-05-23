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
    //CategoryScreen("category","Category",0),
    CategoryScreen("category/{categoryName}", "Category", 0),
    CategoriesScreen("categories","Categories",0),
    //RecipeScreen("recipe","Recipe",0)
    RecipeScreen("recipe/{recipeId}", "Recipe", 0);
    //RecipeScreen("recipe/{recipeId}", "Recipe", 0);

    companion object {
        fun recipeWithId(recipeId: String) = "recipe/$recipeId"
        fun categoryWithName(categoryName: String) = "category/$categoryName"
    }

}
fun AppRoute.recipeRouteWithId(recipeId: Int): String {
    return "recipe/$recipeId"
}