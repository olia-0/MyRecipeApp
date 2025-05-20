package com.example.myrecipeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoriesEntity(
    @PrimaryKey val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    //val strCategoryDescription: String
)