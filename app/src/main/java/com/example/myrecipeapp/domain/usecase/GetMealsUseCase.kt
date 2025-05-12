package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor (
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<Meal> = repository.getRecipes()
}