package com.example.myrecipeapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao


import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity

@Database(entities = [SavedRecipeEntity::class, ViewedRecipeEntity::class], version = 3)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun savedRecipeDao(): SavedRecipeDao
    abstract fun viewedRecipeDao(): ViewedRecipeDao
}
