package com.example.myrecipeapp.domain.usecase

import android.content.Context
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

//class SaveViewedRecipeUseCase @Inject constructor(
//    private val repository: RecipeRepository
//) {
//    suspend operator fun invoke(recipe: Recipe) {
//        repository.saveViewedRecipe(recipe)
//    }
//}

class SaveViewedRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(context: Context, recipe: Recipe, imageUrl: String) {
        repository.saveViewedRecipe2(context,recipe, imageUrl)
    }
}
