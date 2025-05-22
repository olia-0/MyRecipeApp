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
    suspend fun getRecipesByIngredient(ingredient: List<String>): List<RecipeShort>
    suspend fun getRecipesByCategoryAndIngredients(category: String, ingredient: List<String>): List<RecipeShort>
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
    //
    suspend fun saveRecipe2(context: Context,recipe: SavedRecipeEntity, imageUrl: String)
    suspend fun saveViewedRecipe2(context: Context,recipe: Recipe, imageUrl: String)
    ///
    suspend fun getSavedRecipesSizeInfo(): Pair<Long, Long>

    ///
    suspend fun getSavedRecipesByCategory(category: String): List<RecipeShort>
    suspend fun getSavedRecipesByIngredients(ingredient: List<String>): List<RecipeShort>
    suspend fun getSavedRecipesByCategoryAndIngredients(category: String, ingredient: List<String>): List<RecipeShort>

    /////SMART
    suspend fun getRecipesByCategorySmart(category: String): List<RecipeShort>
    suspend fun getRecipesByIngredientsSmart(ingredients: List<String>): List<RecipeShort>
    suspend fun getRecipesByCategoryAndIngredientsSmart(category: String, ingredients: List<String>): List<RecipeShort>

    ////searchName
    suspend fun searchRecipesByName(name: String): List<RecipeShort>
}
