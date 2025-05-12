package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor (
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<RecipeShort> = repository.getRandomRecipes10()
}