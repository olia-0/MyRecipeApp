package com.example.myrecipeapp.domain.usecase

import android.util.Log
import com.example.myrecipeapp.data.mapper.toRecipeShort
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetRandomRecipesUseCase @Inject constructor (
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(): List<RecipeShort> {
        return try {
            Log.d("Юскейс","Репозиторій")
            repository.getRandomRecipes10()
        }catch (e: Exception){
            Log.d("Юскейс",e.toString())
            repository.getSavedRecipes().first().map { it.toRecipeShort() }
        }
    }
}