package com.example.myrecipeapp.domain.usecase

import android.util.Log
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

//class SearchRecipesByIngredientsUseCase @Inject constructor(
//    private val repository: RecipeRepository
//) {
//    suspend operator fun invoke(ingredients: List<String>): List<RecipeShort> {
//        if (ingredients.isEmpty()) return emptyList()
//
//        // Отримуємо рецепти по першому інгредієнту
//        var result = repository.getRecipesByIngredient(ingredients.first())
//
//        // Знаходимо спільні ID
//        for (ingredient in ingredients.drop(1)) {
//            val meals = repository.getRecipesByIngredient(ingredient)
//            val ids = meals.map { it.id }.toSet()
//            result = result.filter { it.id in ids }
//        }
//
//        return result
//    }
//}
class SearchRecipesByIngredientsUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(ingredients: List<String>): List<RecipeShort> {
//        return try {
//            repository.getRecipesByIngredient(ingredients)
//        } catch (e: Exception) {
//            Log.d("SearchRecipesByCategory","Помилка з АПІ")
//            repository.getSavedRecipesByIngredients(ingredients)
//        }
        return repository.getRecipesByIngredientsSmart(ingredients)
    }
//    suspend operator fun invoke(ingredients: List<String>): List<RecipeShort> {
//        if (ingredients.isEmpty()) return emptyList()
//
//        // Отримуємо рецепти по першому інгредієнту
//        var result = repository.getRecipesByIngredient(ingredients)
//
//        // Знаходимо спільні ID
//        for (ingredient in ingredients.drop(1)) {
//            val meals = repository.getRecipesByIngredient(ingredients)
//            val ids = meals.map { it.id }.toSet()
//            result = result.filter { it.id in ids }
//        }
//
//        return result
//    }
}