package com.example.myrecipeapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viewed_recipes")
data class ViewedRecipeEntity(
    @PrimaryKey val id: String,
    //val userId: String,
    val title: String,
    val category: String,
    val ingredients: String,
    val measures: String,
    val steps: String,
    val imagePath: String,
    val viewedAt: Long = System.currentTimeMillis(),// час перегляду
    val imageSizeBytes: Long
)
