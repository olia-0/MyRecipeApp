package com.example.myrecipeapp.domain.usecase

import android.util.Log
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

//class SearchRecipesByCategoryAndIngredientsUseCase @Inject constructor(
//    private val repository: RecipeRepository
//) {
//    suspend operator fun invoke(category: String, ingredients: List<String>): List<RecipeShort> {
//        val byCategory = repository.getRecipesByCategory(category).map { it.id }.toSet()
//        val byIngredients = SearchRecipesByIngredientsUseCase(repository).invoke(ingredients)
//        return byIngredients.filter { it.id in byCategory }
//    }
//}
class SearchRecipesByCategoryAndIngredientsUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(category: String, ingredients: List<String>): List<RecipeShort> {
//        return try {
//            repository.getRecipesByCategoryAndIngredients(category, ingredients)
//        } catch (e: Exception) {
//            Log.d("SearchRecipesByCategory","Помилка з АПІ")
//            repository.getSavedRecipesByCategoryAndIngredients(category, ingredients)
//        }
        return repository.getRecipesByCategoryAndIngredientsSmart(category,ingredients)
    }
}
