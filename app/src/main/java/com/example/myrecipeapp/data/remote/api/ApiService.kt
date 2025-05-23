package com.example.myrecipeapp.data.remote.api

import com.example.myrecipeapp.data.remote.dto.CategoriesListDto
import com.example.myrecipeapp.data.remote.dto.MealsListDto
import com.example.myrecipeapp.data.remote.dto.RecipeShortListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {
    @GET("search.php?f=a")
    suspend fun getMealsByFirstLetter(): MealsListDto

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsListDto

    @GET("random.php")
    suspend fun getRandomRecipe(): RecipeShortListDto


    @GET("filter.php")
    suspend fun filterByCategory(@Query("c") category: String): RecipeShortListDto

    @GET("filter.php")
    suspend fun filterByIngredient(@Query("i") ingredient: String): RecipeShortListDto

    @GET("filter.php")
    suspend fun filterByCategoryAndIngredient(
        @Query("c") category: String,
        @Query("i") ingredient: String
    ): RecipeShortListDto

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoriesListDto

    @GET("search.php")
    suspend fun searchMealsByName(@Query("s") name: String): MealsListDto
}