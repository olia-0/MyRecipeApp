package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedRecipesUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(): Flow<List<Recipe>> {
        return recipeRepository.getSavedRecipes()
    }
}
