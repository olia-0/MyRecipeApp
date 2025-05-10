package com.example.myrecipeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeByCategoryDto(
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("idMeal") val idMeal: String,
)

data class RecipesByCategoryListDto(
    @SerializedName("meals") val meals: List<RecipeByCategoryDto>
)