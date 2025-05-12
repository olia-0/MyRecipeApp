package com.example.myrecipeapp.data.repositoryImpl

import com.example.myrecipeapp.data.mapper.toMealShort
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RecipeRepository {
    override suspend fun getRecipes(): List<Meal> {
        return apiService.getMealsByFirstLetter().meals.map { it.toRecipe() }
    }

    override suspend fun getRecipeById(id: String): Meal {
        return apiService.getMealById(id).meals.first().toRecipe()
    }

//    override suspend fun getRandomRecipe(): Meal {
//        return apiService.getRandomRecipe().meals.first().toRecipe()
//    }
    override suspend fun getRandomRecipes10(): List<RecipeShort> {
        return (1..10).map {
            apiService.getRandomRecipe().meals.first().toMealShort()
        }
    }

    override suspend fun getRecipesByCategory(category: String): List<RecipeShort> {
        return apiService.filterByCategory(category).meals.map { it.toMealShort() } ?: emptyList()
    }

    override suspend fun getRecipesByIngredient(ingredient: String): List<RecipeShort> {
        return apiService.filterByIngredient(ingredient).meals.map { it.toMealShort() } ?: emptyList()
    }
}