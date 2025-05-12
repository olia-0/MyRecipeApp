package com.example.myrecipeapp.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.GetRandomRecipesUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesByIngredientsUseCase: SearchRecipesByIngredientsUseCase
) : ViewModel() {
    private val _randomRecipes = MutableLiveData<List<RecipeShort>>()
    val randomRecipes: LiveData<List<RecipeShort>> = _randomRecipes

    private val _searchRecipesByIngredients = MutableLiveData<List<RecipeShort>>()
    val searchRecipesByIngredients: LiveData<List<RecipeShort>> = _searchRecipesByIngredients

    private val _ingredients = MutableLiveData<List<String>>(emptyList())
    val ingredients: LiveData<List<String>> = _ingredients


    fun fetchRandomRecipe() {
        viewModelScope.launch {
            _randomRecipes.value = getRandomRecipesUseCase()
        }
    }

    fun addIngredient(ingredient: String) {
        val updated = _ingredients.value.orEmpty() + ingredient
        _ingredients.value = updated
        searchByIngredients(updated)
    }

    fun searchByIngredients(ingredients: List<String>) {
        viewModelScope.launch {
            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
        }
    }
    fun searchByIngredients(input: String) {
        val ingredients = input.split(",", ";").map { it.trim() }.filter { it.isNotEmpty() }
        viewModelScope.launch {
            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
        }
    }
}