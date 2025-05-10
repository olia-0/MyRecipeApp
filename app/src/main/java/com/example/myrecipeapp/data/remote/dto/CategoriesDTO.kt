package com.example.myrecipeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDTO(
    @SerializedName("idCategory") val idCategory: String,
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String
)
data class CategoriesListDto(
    @SerializedName("categories") val categories: List<CategoriesDTO>
)