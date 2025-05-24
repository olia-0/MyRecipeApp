package com.example.myrecipeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class SavedRecipeEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val category: String,
    val ingredients: String,
    val measures: String,
    val steps: String,
    val imagePath: String,

    //val lastModified: Long = System.currentTimeMillis(),
    val imageSize: Long //// розмір файлу для контролю обсягу
)