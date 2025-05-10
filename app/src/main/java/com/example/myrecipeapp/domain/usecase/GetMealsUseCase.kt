package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.MealRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor (
    private val repository: MealRepository
) {
    suspend operator fun invoke(): List<Meal> = repository.getMeals()
}