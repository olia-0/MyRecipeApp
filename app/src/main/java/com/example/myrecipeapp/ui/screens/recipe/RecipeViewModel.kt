package com.example.myrecipeapp.ui.screens.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.usecase.GetMealByIdUseCase
import com.example.myrecipeapp.domain.usecase.GetMealsUseCase
import com.example.myrecipeapp.domain.usecase.GetRandomMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getRandomMealUseCase: GetRandomMealUseCase
) : ViewModel() {

    private val _meals = MutableLiveData<List<Meal>>()
    val meals: LiveData<List<Meal>> = _meals

    private val _selectedMeal = MutableLiveData<Meal>()
    val selectedMeal: LiveData<Meal> = _selectedMeal

    private val _randomMeal = MutableLiveData<Meal>()
    val randomMeal: LiveData<Meal> = _randomMeal

    fun fetchMeals() {
        viewModelScope.launch {
            _meals.value = getMealsUseCase()
        }
    }

    fun fetchMealById(id: String) {
        viewModelScope.launch {
            _selectedMeal.value = getMealByIdUseCase(id)
        }
    }

    fun fetchRandomMeal() {
        viewModelScope.launch {
            _randomMeal.value = getRandomMealUseCase()
        }
    }
}