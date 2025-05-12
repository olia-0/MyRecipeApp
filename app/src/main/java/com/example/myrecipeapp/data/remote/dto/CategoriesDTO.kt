package com.example.myrecipeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDTO(
    @SerializedName("strCategory") val strCategory: String,
)
data class CategoriesListDto(
    @SerializedName("meals") val categories: List<CategoriesDTO>
)