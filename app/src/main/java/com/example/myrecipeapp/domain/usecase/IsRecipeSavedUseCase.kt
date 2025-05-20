package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class IsRecipeSavedUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        return recipeRepository.isRecipeSaved(id)
    }
}
