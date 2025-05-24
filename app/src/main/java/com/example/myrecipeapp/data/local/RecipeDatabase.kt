package com.example.myrecipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myrecipeapp.data.local.dao.CategoryDao
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao
import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao


import com.example.myrecipeapp.data.local.entity.CategoryEntity
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity

@Database(
    entities =
    [   SavedRecipeEntity::class,
        ViewedRecipeEntity::class,
        CategoryEntity::class,
    ], version = 7)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun savedRecipeDao(): SavedRecipeDao
    abstract fun viewedRecipeDao(): ViewedRecipeDao
    abstract fun categoryDao(): CategoryDao
}
