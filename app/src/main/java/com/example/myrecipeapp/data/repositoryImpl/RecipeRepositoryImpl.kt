package com.example.myrecipeapp.data.repositoryImpl

import android.content.Context
import android.util.Log
import androidx.datastore.core.IOException
import com.example.myrecipeapp.data.datastore.SettingsDataStore
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.ImageStorage1
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao
import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity
import com.example.myrecipeapp.data.mapper.toMealShort
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.mapper.toRecipe3
import com.example.myrecipeapp.data.mapper.toRecipeShort
import com.example.myrecipeapp.data.mapper.toSavedRecipeEntity
import com.example.myrecipeapp.data.mapper.toViewedEntity
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.data.remote.dto.RecipeDto
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: SavedRecipeDao,
    private val viewedDao: ViewedRecipeDao,
    private val settingsDataStore: SettingsDataStore,
    private val userPrefs: UserPreferences,

    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : RecipeRepository {
    private suspend fun getUserId(): String =
        userPrefs.userProfile.first().uid




    override suspend fun getRecipes(): List<Recipe> {
        return apiService.getMealsByFirstLetter().recipes.map { it.toRecipe() }
    }
    override suspend fun getRecipeById(id: String): Recipe {
        return apiService.getMealById(id).toRecipe()
    }

override suspend fun getRandomRecipes10(): List<RecipeShort> {
    //Log.d("репозиторій",response.toString())
    val response = apiService.getMealsByFirstLetter()
    Log.d("репозиторій",response.toString())
    return response.recipes.map { it.toRecipeShort() }?.take(10) ?: emptyList()
}


    override suspend fun getRecipesByCategory(category: String): List<RecipeShort> {
        return apiService.filterByCategory(category).recipes.map { it.toRecipeShort() } ?: emptyList()
    }

    override suspend fun getRecipesByIngredient(ingredient: String): List<RecipeShort> {
        val response = apiService.filterByIngredient(ingredient)
        return response.recipes.map { it.toRecipeShort() } ?: emptyList()
    }

    override suspend fun getRecipesByCategoryAndIngredients(category: String, ingredients: List<String>): List<RecipeShort> {
        //val ingredients = ingredients.split(",").map { it.trim().lowercase() }

        // Отримуємо рецепти по категорії
        val categoryMeals = apiService.filterByCategory(category).recipes?.map { it.toRecipeShort() } ?: emptyList()

        if (ingredients.isEmpty()) return categoryMeals

        // Отримуємо рецепти по першому інгредієнту
        var commonMeals = apiService.filterByIngredient(ingredients.first()).recipes?.map { it.toRecipeShort() } ?: emptyList()

        // Перетинаємо з іншими інгредієнтами
        for (ingredient in ingredients.drop(1)) {
            val meals = apiService.filterByIngredient(ingredient).recipes?.map { it.toRecipeShort() } ?: emptyList()
            val ids = meals.map { it.id }.toSet()
            commonMeals = commonMeals.filter { it.id in ids }
        }

        // Повертаємо тільки ті рецепти, що є і в категорії, і в списку за інгредієнтами
        val categoryIds = categoryMeals.map { it.id }.toSet()
        return commonMeals.filter { it.id in categoryIds }
    }

    ////////Room
    ///////////////Saved
    override suspend fun saveRecipe(recipe: SavedRecipeEntity) {
        return dao.insert(recipe)
        //val count = dao.getCount()
    }

    //override suspend fun getSavedRecipes(): List<SavedRecipeEntity> = dao.getAll()
    override suspend fun getSavedRecipes(): Flow<List<Recipe>> {
        val userId = getUserId()
        //return dao.getAll().map { it.toRecipe() }
        return dao.getAll(userId).map { list ->
            list.map { it.toRecipe() }
        }
    }
    override fun getViewedRecipes(): Flow<List<Recipe>> {
        return viewedDao.getAllViewedRecipes().map { list ->
            list.map { it.toRecipe() }
        }
    }



    override suspend fun deleteRecipeById(id: String) {
        val userId = getUserId()
        return dao.deleteById(id, userId)
    }

    override suspend fun downloadRecipeImage(context: Context, url: String, id: String): String? {
        return ImageStorage1.downloadAndSaveImage(context, url, "recipe_$id")
    }
    override suspend fun isRecipeSaved(id: String): Boolean {
        val userId = getUserId()
        return dao.getById(id, userId) != null
    }

    ///////
    override suspend fun getRecipeByIdWithFallback(id: String): Recipe {
        return try {
            // Пробуємо отримати з API
            val recipe = apiService.getMealById(id).toRecipe()
            recipe
        } catch (e: Exception) {
            val userId = getUserId()
            try {
                dao.getById(id, userId)?.toRecipe() ?: throw e
            }catch (e: Exception){
                viewedDao.getById(id)?.toRecipe() ?: throw e
            }
            // Якщо сталася помилка (немає інтернету), повертаємо з бази
            //dao.getById(id, userId)?.toRecipe() ?: throw e
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

//    override fun getViewedRecipes(): Flow<List<Recipe>> =
//        viewedDao.getAllViewedRecipes().map { list -> list.map { it.toRecipe() } }



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
        override suspend fun saveRecipe2(context: Context,recipe: Recipe, imageUrl: String) {

            val userId = getUserId()
            val imageResult = ImageStorage.downloadAndSaveImage(context, imageUrl, "recipe_${recipe.idRecipe}")
                ?: throw Exception("Не вдалося завантажити зображення")

            val (imagePath, imageSize) = imageResult

            val currentCount = dao.getCount(userId)
            val currentSize = dao.getTotalSize(userId) ?: 0L

            if (currentCount >= maxSavedRecipes) {
                throw Exception("Досягнуто максимальну кількість збережених рецептів")
            }

            if (currentSize + imageSize > maxImageSizeBytes) {
                freeSpaceForSavedRecipes(imageSize)
                val newSize = dao.getTotalSize(userId) ?: 0L
                if (newSize + imageSize > maxImageSizeBytes) {
                    throw Exception("Перевищено максимальний обсяг збережених рецептів")
                }
            }

            val toSave = recipe.toSavedRecipeEntity(getUserId(), recipe.photoRecipe?: "").copy(imagePath = imagePath, imageSize = imageSize)
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
            //userId = getUserId(),
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
        val userId = getUserId()
        val currentSize = dao.getTotalSize(userId) ?: 0L
        return Pair(currentSize, maxImageSizeBytes)
    }

    /////пошук в базі
//    override suspend fun getSavedRecipesByCategory(category: String): List<RecipeShort> {
//        val saved = dao.getByCategory(category)
//        val viewed = viewedDao.getByCategory(category)
//        val combined = (saved + viewed).distinctBy { it.id }
//        return combined.map { it.toRecipeShort() }
//    }
    override suspend fun getSavedRecipesByCategory(category: String): List<RecipeShort> {
        val userId = getUserId()
        val saved = dao.getByCategory(category, userId).first().map { it.toRecipeShort() }
        val viewed = viewedDao.getByCategory(category).first().map { it.toRecipeShort() }
        val combined = (saved + viewed).distinctBy { it.id }
        return combined
    }

override suspend fun getSavedRecipesByIngredients(ingredients: List<String>): List<RecipeShort> {
    val userId = getUserId()
    val savedList = dao.getAll(userId).first()
    val viewedList = viewedDao.getAllViewedRecipes().first()

    val savedFiltered = savedList.filter { recipe ->
        val recipeIngredients = recipe.ingredients.split(",").map { it.trim().lowercase() }
        ingredients.all { it.trim().lowercase() in recipeIngredients }
    }.map { it.toRecipeShort() }

    val viewedFiltered = viewedList.filter { recipe ->
        val recipeIngredients = recipe.ingredients.split(",").map { it.trim().lowercase() }
        ingredients.all { it.trim().lowercase() in recipeIngredients }
    }.map { it.toRecipeShort() }

    return (savedFiltered + viewedFiltered).distinctBy { it.id }
}
//
    override suspend fun getSavedRecipesByCategoryAndIngredients(category: String, ingredients: List<String>): List<RecipeShort> {
        val userId = getUserId()
        val savedList = dao.getByCategory(category, userId).first()
        val viewedList = viewedDao.getByCategory(category).first()

        val savedFiltered = savedList.filter { recipe ->
            val recipeIngredients = recipe.ingredients.split(",").map { it.trim().lowercase() }
            ingredients.all { it.trim().lowercase() in recipeIngredients }
        }.map { it.toRecipeShort() }

        val viewedFiltered = viewedList.filter { recipe ->
            val recipeIngredients = recipe.ingredients.split(",").map { it.trim().lowercase() }
            ingredients.all { it.trim().lowercase() in recipeIngredients }
        }.map { it.toRecipeShort() }

        return (savedFiltered + viewedFiltered).distinctBy { it.id }
    }

    /////SMART
    override suspend fun getRecipesByCategorySmart(category: String): List<RecipeShort> {
        return try {
            getRecipesByCategory(category)
        } catch (e: IOException) {//catch (e: Exception) {
          getSavedRecipesByCategory(category)
        }
    }
    override suspend fun getRecipesByIngredientsSmart(ingredients: List<String>): List<RecipeShort> {
        return try {
            Log.d("Я тут Smart",ingredients.toString())
            var result = getRecipesByIngredient(ingredients.first())
            Log.d("Я тут Smart Результат",ingredients.first())

            // Знаходимо спільні ID
            for (ingredient in ingredients.drop(1)) {
                Log.d("ingredient", ingredient)

                val meals = getRecipesByIngredient(ingredient)
                Log.d("meals", meals.toString())

                val ids = meals.map { it.id }.toSet()
                Log.d("ids", ids.toString())

                result = result.filter { it.id in ids }
                Log.d("result", result.toString())
            }

            result
            //getRecipesByIngredient(ingredients)
        } catch (e: IOException) {//catch (e: Exception) {
            Log.d("Я тут Smart Збережені",ingredients.toString())
            getSavedRecipesByIngredients(ingredients)
        }
    }
    override suspend fun getRecipesByCategoryAndIngredientsSmart(category: String, ingredients: List<String>): List<RecipeShort> {
        return try {
            getRecipesByCategoryAndIngredients(category, ingredients)
        } catch (e: IOException) {//catch (e: Exception) {
            getSavedRecipesByCategoryAndIngredients(category, ingredients)
        }
    }

    ///searchName
    override suspend fun searchRecipesByName(name: String): List<Recipe> {
        val response = apiService.searchMealsByName(name)
        return response.recipes.map { it.toRecipe() } ?: emptyList()
    }

    override suspend fun addRecipe(recipe: RecipeDto) {
        val userId = getUserId()
        val finalRecipe = recipe.copy(
            userId = userId,
            photoRecipe = recipe.photoRecipe ?: "",
            categoryRecipe = recipe.categoryRecipe ?: "",
            areaRecipe = recipe.areaRecipe ?: "",
            instructionsRecipe = recipe.instructionsRecipe ?: "",
            tagsRecipe = recipe.tagsRecipe ?: "",
            youtubeRecipe = recipe.youtubeRecipe ?: "",
            description = recipe.description ?: "",
            cookingTime = recipe.cookingTime ?: "",
            nutritionCalories = recipe.nutritionCalories ?: 0,
            nutritionProteins = recipe.nutritionProteins ?: 0f,
            nutritionFats = recipe.nutritionFats ?: 0f,
            nutritionCarbs = recipe.nutritionCarbs ?: 0f,
            idRecipe = recipe.idRecipe.ifBlank { UUID.randomUUID().toString() }
        )

        apiService.addRecipe(finalRecipe)
    }





}


