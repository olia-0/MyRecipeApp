package com.example.myrecipeapp.domain.model

import androidx.room.PrimaryKey

data class SavedRecipe(
    val id: String,
    val title: String,
    val category: String,
    val ingredients: String,
    val steps: String,
    val imagePath: String
)