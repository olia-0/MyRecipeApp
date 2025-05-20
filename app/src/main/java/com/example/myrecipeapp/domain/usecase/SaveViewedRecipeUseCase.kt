package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class SaveViewedRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.saveViewedRecipe(recipe)
    }
}
