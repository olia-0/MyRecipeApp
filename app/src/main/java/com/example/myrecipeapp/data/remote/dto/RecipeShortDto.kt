package com.example.myrecipeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeShortDto(
    @SerializedName("strMeal") val strMeal: String,
    @SerializedName("strMealThumb") val strMealThumb: String,
    @SerializedName("idMeal") val idMeal: String,
)

data class RecipeShortListDto(
    @SerializedName("recipes") val meals: List<RecipeShortDto>
)

