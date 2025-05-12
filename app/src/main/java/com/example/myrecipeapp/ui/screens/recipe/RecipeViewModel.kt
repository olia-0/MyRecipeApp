package com.example.myrecipeapp.ui.screens.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.usecase.GetMealByIdUseCase
import com.example.myrecipeapp.domain.usecase.GetMealsUseCase
import com.example.myrecipeapp.domain.usecase.MealInstructionsUseCase
import com.example.myrecipeapp.domain.usecase.YouTubeIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    //private val getRandomMealUseCase: GetRandomRecipesUseCase,
    private val mealInstructionsUseCase: MealInstructionsUseCase,
    private val idVideoUseCase : YouTubeIdUseCase
) : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    private val _selectedMeal = MutableLiveData<Meal>()
    val selectedMeal: LiveData<Meal> = _selectedMeal

    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

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
            val meal = getMealByIdUseCase(id)
            _selectedMeal.value = meal//getMealByIdUseCase(id)
            meal.instructionsRecipe?.let {
                _mealInstructions.value = mealInstructionsUseCase.splitInstructions(it)
            }
            youtubeId = idVideoUseCase.extractYouTubeId(selectedMeal.value?.youtubeRecipe)
            Log.d("AAAA Video 1 ",youtubeId ?: "ooops")
            //val youtubeId = idVideoUseCase.extractYouTubeId(meal.youtubeRecipe)
        }
    }

//    fun fetchRandomMeal() {
//        viewModelScope.launch {
//            _randomMeal.value = getRandomMealUseCase()
//        }
//    }
}