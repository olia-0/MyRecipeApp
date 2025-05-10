package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.domain.model.Meal

interface MealRepository  {
    suspend fun getMeals(): List<Meal>
    suspend fun getMealById(id: String): Meal
    suspend fun getRandomMeal(): Meal
}
