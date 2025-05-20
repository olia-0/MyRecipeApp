package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetSavedRecipesSizeUseCase @Inject constructor(
    private val repo: RecipeRepository
) {
    suspend operator fun invoke(): Pair<Long, Long> {
        return repo.getSavedRecipesSizeInfo()
    }
}
