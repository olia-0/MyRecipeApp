package com.example.myrecipeapp.domain.repository

import android.content.Context
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import kotlinx.coroutines.flow.Flow

interface RecipeRepository  {
    suspend fun getRecipes(): List<Recipe>
    suspend fun getRecipeById(id: String): Recipe
    //suspend fun getRandomRecipe(): Meal
    suspend fun getRandomRecipes10(): List<RecipeShort>

    suspend fun getRecipesByCategory(category: String): List<RecipeShort>
    suspend fun getRecipesByIngredient(ingredient: String): List<RecipeShort>
    ////Room
    //////////Saved
    suspend fun saveRecipe(recipe: SavedRecipeEntity)

    suspend fun getSavedRecipes(): Flow<List<Recipe>>

    suspend fun deleteRecipeById(id: String)

    suspend fun downloadRecipeImage(context: Context, url: String, id: String): String?

    suspend fun isRecipeSaved(id: String): Boolean
    ///////
    suspend fun getRecipeByIdWithFallback(id: String): Recipe

    ////////Viewed
    suspend fun saveViewedRecipe(recipe: Recipe)
    fun getViewedRecipes(): Flow<List<Recipe>>
}
