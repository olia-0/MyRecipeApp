package com.example.myrecipeapp.data.repositoryImpl

import android.content.Context
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao
import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.mapper.toMealShort
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.mapper.toViewedEntity
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: SavedRecipeDao,
    private val viewedDao: ViewedRecipeDao
) : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return apiService.getMealsByFirstLetter().meals.map { it.toRecipe() }
    }

    override suspend fun getRecipeById(id: String): Recipe {
        return apiService.getMealById(id).meals.first().toRecipe()
    }

//    override suspend fun getRandomRecipe(): Meal {
//        return apiService.getRandomRecipe().meals.first().toRecipe()
//    }
    override suspend fun getRandomRecipes10(): List<RecipeShort> {
        return (1..10).map {
            apiService.getRandomRecipe().meals.first().toMealShort()
        }
    }

    override suspend fun getRecipesByCategory(category: String): List<RecipeShort> {
        return apiService.filterByCategory(category).meals.map { it.toMealShort() } ?: emptyList()
    }

    override suspend fun getRecipesByIngredient(ingredient: String): List<RecipeShort> {
        return apiService.filterByIngredient(ingredient).meals.map { it.toMealShort() } ?: emptyList()
    }
    ////////Room
    ///////////////Saved
    override suspend fun saveRecipe(recipe: SavedRecipeEntity) = dao.insert(recipe)

    //override suspend fun getSavedRecipes(): List<SavedRecipeEntity> = dao.getAll()
    override suspend fun getSavedRecipes(): Flow<List<Recipe>> {
        //return dao.getAll().map { it.toRecipe() }
        return dao.getAll().map { list ->
            list.map { it.toRecipe() }
        }
    }


    override suspend fun deleteRecipeById(id: String) = dao.deleteById(id)

    override suspend fun downloadRecipeImage(context: Context, url: String, id: String): String? {
        return ImageStorage.downloadAndSaveImage(context, url, "recipe_$id")
    }
    override suspend fun isRecipeSaved(id: String): Boolean {
        return dao.getById(id) != null
    }

    ///////
    override suspend fun getRecipeByIdWithFallback(id: String): Recipe {
        return try {
            // Пробуємо отримати з API
            val recipe = apiService.getMealById(id).meals.first().toRecipe()
            recipe
        } catch (e: Exception) {
            // Якщо сталася помилка (немає інтернету), повертаємо з бази
            dao.getById(id)?.toRecipe() ?: throw e
        }
    }
    ///////////////Viewed
    override suspend fun saveViewedRecipe(recipe: Recipe) {
        val entity = recipe.toViewedEntity()
        viewedDao.insert(entity)

        // Кеш обмеження (наприклад, 100 рецептів)
        val count = viewedDao.getCount()
        if (count > 100) {
            val oldRecipes = viewedDao.getOldestRecipes(count - 100)
            oldRecipes.forEach { viewedDao.deleteById(it.id) }
        }
    }

    override fun getViewedRecipes(): Flow<List<Recipe>> =
        viewedDao.getAllViewedRecipes().map { list -> list.map { it.toRecipe() } }

}