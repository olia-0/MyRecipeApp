package com.example.myrecipeapp.ui.screens.recipe

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.mapper.toSavedRecipeEntity
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.usecase.DeleteRecipeUseCase
import com.example.myrecipeapp.domain.usecase.DownloadRecipeImageUseCase
import com.example.myrecipeapp.domain.usecase.GetMealByIdUseCase
import com.example.myrecipeapp.domain.usecase.GetMealsUseCase
import com.example.myrecipeapp.domain.usecase.GetRecipeByIdWithFallbackUseCase
import com.example.myrecipeapp.domain.usecase.IsRecipeSavedUseCase
import com.example.myrecipeapp.domain.usecase.MealInstructionsUseCase
import com.example.myrecipeapp.domain.usecase.SaveRecipeUseCase
import com.example.myrecipeapp.domain.usecase.SaveViewedRecipeUseCase
import com.example.myrecipeapp.domain.usecase.YouTubeIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    //private val getRandomMealUseCase: GetRandomRecipesUseCase,
    private val mealInstructionsUseCase: MealInstructionsUseCase,
    private val idVideoUseCase : YouTubeIdUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val downloadImageUseCase: DownloadRecipeImageUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val isRecipeSavedUseCase: IsRecipeSavedUseCase,
    private val getRecipeByIdWithFallbackUseCase: GetRecipeByIdWithFallbackUseCase,
    private val saveViewedRecipeUseCase: SaveViewedRecipeUseCase
) : ViewModel() {

    private val _meals = MutableLiveData<List<Recipe>>()
    val meals: LiveData<List<Recipe>> = _meals

    private val _selectedMeal = MutableLiveData<Recipe>()
    val selectedMeal: LiveData<Recipe> = _selectedMeal

    private val _randomMeal = MutableLiveData<Recipe>()
    val randomMeal: LiveData<Recipe> = _randomMeal

    private val _mealInstructions = MutableLiveData<List<String>>()
    val mealInstructions: LiveData<List<String>> = _mealInstructions

    //private var _youtubeId: String = ""
    var youtubeId: String? = null



    fun fetchMeals() {
        viewModelScope.launch {
            _meals.value = getMealsUseCase()
        }
    }

    fun fetchMealById(id: String) {
        viewModelScope.launch {
            val meal = getRecipeByIdWithFallbackUseCase(id)
            _selectedMeal.value = meal
            meal.instructionsRecipe?.let {
                _mealInstructions.value = mealInstructionsUseCase.splitInstructions(it)
            }
            youtubeId = idVideoUseCase.extractYouTubeId(meal.youtubeRecipe)
        }
    }
    ////Room
    fun saveRecipeWithImage(context: Context, recipe: Recipe) {
        viewModelScope.launch {
            try {
                val imagePath = recipe.photoRecipe?.let {
                    downloadImageUseCase(context,
                        it, recipe.idRecipe)
                }
                val savedEntity = imagePath?.let { recipe.toSavedRecipeEntity(it) }
                savedEntity?.let { saveRecipeUseCase(context,it,recipe.photoRecipe ?: "") }
            }catch (e: Exception){
                Log.d("Помилка в збережені фото рецепту", e.toString())
            }

        }
    }

    fun deleteSavedRecipe(id: String) {
        viewModelScope.launch {
            deleteRecipeUseCase(id)
        }
    }

    suspend fun isRecipeSaved(id: String): Boolean = withContext(Dispatchers.IO) {
        isRecipeSavedUseCase(id)
    }

    fun saveRecipeViewedWithImage(context: Context,recipe: Recipe) {
        viewModelScope.launch {
            saveViewedRecipeUseCase(context,recipe, recipe.photoRecipe ?: "")
        }
    }


    //fun getLocalImagePath(id: String): String = ImageStorage.getImageDir()//.getImagePath("recipe_$id")



//    fun fetchRandomMeal() {
//        viewModelScope.launch {
//            _randomMeal.value = getRandomMealUseCase()
//        }
//    }
}