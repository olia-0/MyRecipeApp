package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort

interface RecipeRepository  {
    suspend fun getRecipes(): List<Meal>
    suspend fun getRecipeById(id: String): Meal
    //suspend fun getRandomRecipe(): Meal
    suspend fun getRandomRecipes10(): List<RecipeShort>

    suspend fun getRecipesByCategory(category: String): List<RecipeShort>
    suspend fun getRecipesByIngredient(ingredient: String): List<RecipeShort>

}
