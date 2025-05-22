package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class SearchRecipesByNameUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(name: String): List<RecipeShort> {
        if (name.isBlank()) return emptyList()
        return repository.searchRecipesByName(name)
    }
}
