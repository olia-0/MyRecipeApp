package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class DeleteRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(id: String) {
        recipeRepository.deleteRecipeById(id)
    }
}
