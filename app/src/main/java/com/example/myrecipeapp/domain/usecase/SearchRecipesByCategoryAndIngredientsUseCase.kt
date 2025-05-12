package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository

class SearchRecipesByCategoryAndIngredientsUseCase(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(category: String, ingredients: List<String>): List<RecipeShort> {
        val byCategory = repository.getRecipesByCategory(category).map { it.id }.toSet()
        val byIngredients = SearchRecipesByIngredientsUseCase(repository).invoke(ingredients)
        return byIngredients.filter { it.id in byCategory }
    }
}