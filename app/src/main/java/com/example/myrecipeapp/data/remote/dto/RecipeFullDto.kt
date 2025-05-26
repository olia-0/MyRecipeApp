package com.example.myrecipeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeFullDto(
    @SerializedName("idRecipe") val idRecipe: String,
    @SerializedName("nameRecipe") val nameRecipe: String,
    @SerializedName("photoRecipe") val photoRecipe: String?,
    @SerializedName("categoryRecipe") val categoryRecipe: String?,
    @SerializedName("areaRecipe") val areaRecipe: String?,
    @SerializedName("instructionsRecipe") val instructionsRecipe: String?,
    @SerializedName("tagsRecipe") val tagsRecipe: String?,
    @SerializedName("youtubeRecipe") val youtubeRecipe: String?,
    @SerializedName("ingredients") val ingredients: String,
    @SerializedName("measures") val measures: String,
    @SerializedName("description") val description: String?,
    @SerializedName("nutritionCalories") val nutritionCalories: Int?,
    @SerializedName("nutritionProteins") val nutritionProteins: Float?,
    @SerializedName("nutritionFats") val nutritionFats: Float?,
    @SerializedName("nutritionCarbs") val nutritionCarbs: Float?,
    @SerializedName("cookingTime") val cookingTime: String?,
    @SerializedName("userId") val userId: String?
)

data class RecipesListFullDto(
    @SerializedName("recipes") val recipes: List<RecipeFullDto>
)
