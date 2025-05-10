package com.example.myrecipeapp.data.repositoryImpl

import com.example.myrecipeapp.data.mapper.toMeal
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.MealRepository
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MealRepository {
    override suspend fun getMeals(): List<Meal> {
        return apiService.getMealsByFirstLetter().meals.map { it.toMeal() }
    }

    override suspend fun getMealById(id: String): Meal {
        return apiService.getMealById(id).meals.first().toMeal()
    }

    override suspend fun getRandomMeal(): Meal {
        return apiService.getRandomMeal().meals.first().toMeal()
    }
}