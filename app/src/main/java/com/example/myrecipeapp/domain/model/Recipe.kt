package com.example.myrecipeapp.domain.model

data class Recipe(
    val idRecipe: String,
    val nameRecipe: String,
    val photoRecipe: String?,
    val categoryRecipe: String?,
    val areaRecipe: String?,
    val instructionsRecipe: String?,
    val tagsRecipe: String?,
    val youtubeRecipe: String?,
    val ingredients: String,
    val measures: String
)
