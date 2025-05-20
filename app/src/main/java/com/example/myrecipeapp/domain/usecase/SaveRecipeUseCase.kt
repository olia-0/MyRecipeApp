package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class SaveRecipeUseCase @Inject constructor(private val repo: RecipeRepository) {
    suspend operator fun invoke(recipe: SavedRecipeEntity) = repo.saveRecipe(recipe)
}
