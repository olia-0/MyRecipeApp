package com.example.myrecipeapp.domain.usecase

import android.util.Log
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

//class SearchRecipesByCategoryUseCase @Inject constructor(
//    private val repository: RecipeRepository
//) {
//    suspend operator fun invoke(category: String): List<RecipeShort> {
//        return repository.getRecipesByCategory(category)
//    }
//}
class SearchRecipesByCategoryUseCase @Inject constructor(
    private val repository: RecipeRepository,
) {
    suspend operator fun invoke(category: String): List<RecipeShort> {
        return try {
            repository.getRecipesByCategory(category)
        } catch (e: Exception) {
            Log.d("SearchRecipesByCategory","Помилка з АПІ")
            repository.getSavedRecipesByCategory(category)
        }
    }
}

