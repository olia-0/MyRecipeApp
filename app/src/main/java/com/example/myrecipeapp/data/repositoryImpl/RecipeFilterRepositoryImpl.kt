package com.example.myrecipeapp.data.repositoryImpl

import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeFilterRepository
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class RecipeFilterRepositoryImpl @Inject constructor(
    private val baseRepository: RecipeRepository
) : RecipeFilterRepository {
    override suspend fun getRecipesByCategoryAndIngredient(category: String, ingredient: String): List<RecipeShort> {
        val recipesByCategory = baseRepository.getRecipesByCategory(category)
        val recipesByIngredient = baseRepository.getRecipesByIngredient(ingredient)
        val commonIds = recipesByIngredient.map { it.id }.toSet()
        return recipesByCategory.filter { it.id in commonIds }
    }

    override suspend fun getRecipesByIngredients(ingredients: List<String>): List<RecipeShort> {
        if (ingredients.isEmpty()) return emptyList()

        // Отримуємо рецепти для першого інгредієнта
        var result = baseRepository.getRecipesByIngredient(ingredients.first())

        // Знаходимо перетин з іншими інгредієнтами
        for (ingredient in ingredients.drop(1)) {
            val meals = baseRepository.getRecipesByIngredient(ingredient)
            val ids = meals.map { it.id }.toSet()
            result = result.filter { it.id in ids }
        }

        return result
    }
}