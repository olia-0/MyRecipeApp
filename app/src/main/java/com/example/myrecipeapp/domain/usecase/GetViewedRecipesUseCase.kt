package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetViewedRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<List<Recipe>> {
        return repository.getViewedRecipes()
    }
}
