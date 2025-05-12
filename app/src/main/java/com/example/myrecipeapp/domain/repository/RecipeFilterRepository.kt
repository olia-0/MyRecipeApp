package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort

interface RecipeFilterRepository{
    suspend fun getRecipesByCategoryAndIngredient(category: String, ingredient: String): List<RecipeShort>
    suspend fun getRecipesByIngredients(ingredients: List<String>): List<RecipeShort>
}