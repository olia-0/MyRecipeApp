package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Meal

interface CategoriesRepository {
    suspend fun getCategories(): List<Categories>
}