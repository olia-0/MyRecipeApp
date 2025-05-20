package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeByIdWithFallbackUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: String): Recipe {
        return repository.getRecipeByIdWithFallback(id)
    }
}
