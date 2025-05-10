package com.example.myrecipeapp.data.remote.api

import com.example.myrecipeapp.data.remote.dto.MealsListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService  {
    @GET("search.php?f=a")
    suspend fun getMealsByFirstLetter(): MealsListDto

    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: String): MealsListDto

    @GET("random.php")
    suspend fun getRandomMeal(): MealsListDto
}