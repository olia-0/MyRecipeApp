package com.example.myrecipeapp.data.remote.api

import com.example.myrecipeapp.data.remote.dto.CategoriesListDto
import com.example.myrecipeapp.data.remote.dto.MealsListDto
import com.example.myrecipeapp.data.remote.dto.RecipeDto
import com.example.myrecipeapp.data.remote.dto.RecipeShortListDto
import com.example.myrecipeapp.data.remote.dto.RecipesListDto
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface ApiService  {
//    @GET("search.php?f=a")
//    suspend fun getMealsByFirstLetter(): MealsListDto
//
//    @GET("lookup.php")
//    suspend fun getMealById(@Query("i") id: String): MealsListDto
//
//    @GET("random.php")
//    suspend fun getRandomRecipe(): RecipeShortListDto
//
//
//    @GET("filter.php")
//    suspend fun filterByCategory(@Query("c") category: String): RecipeShortListDto
//
//    @GET("filter.php")
//    suspend fun filterByIngredient(@Query("i") ingredient: String): RecipeShortListDto
//
//    @GET("filter.php")
//    suspend fun filterByCategoryAndIngredient(
//        @Query("c") category: String,
//        @Query("i") ingredient: String
//    ): RecipeShortListDto
//
//    @GET("list.php?c=list")
//    suspend fun getCategories(): CategoriesListDto
//
//    @GET("search.php")
//    suspend fun searchMealsByName(@Query("s") name: String): MealsListDto
//}
interface ApiService {

    // Замінено на локальний пошук рецептів за першою літерою (передаємо параметр в query)
    @GET("api/recipes/search")
    suspend fun getMealsByFirstLetter(@Query("name") letter: String = "а"): RecipesListDto

    // Отримання рецепту за ID
    //@GET("api/recipes/{id}")
    //suspend fun getMealById(@Path("id") id: String): RecipesListDto
    @GET("api/recipes/{id}")
    suspend fun getMealById(@Path("id") id: String): RecipeDto


    // Випадковий рецепт
    //@GET("api/recipes/random")
    //suspend fun getRandomRecipe(): RecipeShortListDto

    // Фільтрація за категорією
    @GET("api/recipes/search")
    suspend fun filterByCategory(@Query("category") category: String): RecipesListDto

    // Фільтрація за інгредієнтом
    @GET("api/recipes/search")
    suspend fun filterByIngredient(@Query("ingredient") ingredient: String): RecipesListDto

    // Фільтрація за категорією та інгредієнтом (тут можна передати два параметри)
    @GET("api/recipes/search")
    suspend fun filterByCategoryAndIngredient(
        @Query("category") category: String,
        @Query("ingredient") ingredient: String
    ): RecipeShortListDto

    // Список категорій
    @GET("api/categories")
    suspend fun getCategories(): CategoriesListDto

    // Пошук рецептів за назвою
    @GET("api/recipes/search")
    suspend fun searchMealsByName(@Query("name") name: String): RecipesListDto
}

