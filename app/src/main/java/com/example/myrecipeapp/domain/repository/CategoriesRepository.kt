package com.example.myrecipeapp.domain.repository

import com.example.myrecipeapp.data.local.entity.CategoryEntity
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Meal

interface CategoriesRepository {
    suspend fun getCategories(): List<Categories>
    suspend fun getCategoriesDB(): List<Category>
    suspend fun saveCategory(category: CategoryEntity)
    suspend fun getCategoryByName(name: String): Category
}