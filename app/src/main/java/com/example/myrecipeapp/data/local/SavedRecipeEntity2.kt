package com.example.myrecipeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "saved_recipe")
//data class SavedRecipeEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
//    @ColumnInfo(name = "recipe_id")
//    val recipeid: String,
//    @ColumnInfo(name = "recipe_name")
//    val recipename: String,
//    @ColumnInfo(name = "recipe_photo")
//    val recipephoto: String?,
////    @ColumnInfo(name = "recipe_status")
////    val recipe_status: Boolean
//)
//@Entity(tableName = "saved_recipes")
//data class SavedRecipeEntity(
//    @PrimaryKey val id: String,
//    val name: String,
//    val imageUrl: String?,
//    val ingredients: List<String>,
//    val instructions: String,
//    val timestamp: Long = System.currentTimeMillis() // для сортування по часу
//)
@Entity(tableName = "saved_recipes")
data class SavedRecipeEntity2(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val ingredients: String,
    val steps: String,
    val imagePath: String
)