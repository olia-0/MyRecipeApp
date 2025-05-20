package com.example.myrecipeapp.data.repositoryImpl

import android.content.Context
import androidx.datastore.core.IOException
import com.example.myrecipeapp.data.datastore.SettingsDataStore
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.ImageStorage1
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao
import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity
import com.example.myrecipeapp.data.mapper.toMealShort
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.mapper.toViewedEntity
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: SavedRecipeDao,
    private val viewedDao: ViewedRecipeDao,
    private val settingsDataStore: SettingsDataStore,

    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
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
    override suspend fun saveRecipe(recipe: SavedRecipeEntity) {
        return dao.insert(recipe)
        //val count = dao.getCount()
    }

    //override suspend fun getSavedRecipes(): List<SavedRecipeEntity> = dao.getAll()
    override suspend fun getSavedRecipes(): Flow<List<Recipe>> {
        //return dao.getAll().map { it.toRecipe() }
        return dao.getAll().map { list ->
            list.map { it.toRecipe() }
        }
    }


    override suspend fun deleteRecipeById(id: String) = dao.deleteById(id)

    override suspend fun downloadRecipeImage(context: Context, url: String, id: String): String? {
        return ImageStorage1.downloadAndSaveImage(context, url, "recipe_$id")
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



    //////////////////////////
    // Кешовані значення налаштувань
    // Кешовані значення, оновлюються при зміні в DataStore
    @Volatile
    private var maxSavedRecipes: Int = SettingsDataStore.DEFAULT_MAX_RECIPES

    @Volatile
    private var maxViewedRecipes: Int = SettingsDataStore.DEFAULT_MAX_VIEWED_RECIPES

    @Volatile
    private var maxImageSizeBytes: Long = SettingsDataStore.DEFAULT_MAX_IMAGE_MB * 1024L * 1024L

    init {
        coroutineScope.launch {
            settingsDataStore.maxSavedRecipes.collect {
                maxSavedRecipes = it
            }
        }
        coroutineScope.launch {
            settingsDataStore.maxViewedRecipes.collect {
                maxViewedRecipes = it
            }
        }
        coroutineScope.launch {
            settingsDataStore.maxImageSizeMB.collect {
                maxImageSizeBytes = it * 1024L * 1024L
            }
        }
    }

        //
        override suspend fun saveRecipe2(context: Context,recipe: SavedRecipeEntity, imageUrl: String) {

            val imageResult = ImageStorage.downloadAndSaveImage(context, imageUrl, "recipe_${recipe.id}")
                ?: throw Exception("Не вдалося завантажити зображення")

            val (imagePath, imageSize) = imageResult

            val currentCount = dao.getCount()
            val currentSize = dao.getTotalSize() ?: 0L

            if (currentCount >= maxSavedRecipes) {
                throw Exception("Досягнуто максимальну кількість збережених рецептів")
            }

            if (currentSize + imageSize > maxImageSizeBytes) {
                freeSpaceForSavedRecipes(imageSize)
                val newSize = dao.getTotalSize() ?: 0L
                if (newSize + imageSize > maxImageSizeBytes) {
                    throw Exception("Перевищено максимальний обсяг збережених рецептів")
                }
            }

            val toSave = recipe.copy(imagePath = imagePath, imageSize = imageSize)
            dao.insert(toSave)
        }


    private suspend fun freeSpaceForSavedRecipes(neededBytes: Long) {
        val viewed = viewedDao.getAllViewedRecipes().first()
        var freedBytes = 0L

        val sortedViewed = viewed.sortedBy { it.viewedAt }

        for (viewedRecipe in sortedViewed) {
            if (freedBytes >= neededBytes) break
            viewedDao.deleteById(viewedRecipe.id)
            File(viewedRecipe.imagePath).delete()
            freedBytes += viewedRecipe.imageSizeBytes
        }
    }

    override suspend fun saveViewedRecipe2(context: Context,recipe: Recipe, imageUrl: String) {
        val imageResult = ImageStorage.downloadAndSaveImage(context, imageUrl, "viewed_${recipe.idRecipe}")
            ?: return

        val (imagePath, imageSize) = imageResult

        val cutoffTime = System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000
        viewedDao.deleteOlderThan(cutoffTime)

        val count = viewedDao.getCount()
        if (count >= maxViewedRecipes) {
            val overflow = count - maxViewedRecipes + 1
            val oldest = viewedDao.getOldestRecipes(overflow)
            oldest.forEach {
                viewedDao.deleteById(it.id)
                File(it.imagePath).delete()
            }
        }

        val viewedEntity = ViewedRecipeEntity(
            id = recipe.idRecipe,
            title = recipe.nameRecipe,
            imagePath = imagePath,
            viewedAt = System.currentTimeMillis(),
            imageSizeBytes = imageSize,
            category = recipe.categoryRecipe ?: "",
            steps = recipe.instructionsRecipe ?: "",
            measures = recipe.measures,
            ingredients = recipe.ingredients
        )
        viewedDao.insert(viewedEntity)
    }
    /////
    override suspend fun getSavedRecipesSizeInfo(): Pair<Long, Long> {
        val currentSize = dao.getTotalSize() ?: 0L
        return Pair(currentSize, maxImageSizeBytes)
    }



}


