package com.example.myrecipeapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.GetCategoriesUseCase
import com.example.myrecipeapp.domain.usecase.GetRandomRecipesUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByCategoryAndIngredientsUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByCategoryUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByIngredientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesByIngredientsUseCase: SearchRecipesByIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchRecipesByCategoryAndIngredientsUseCase: SearchRecipesByCategoryAndIngredientsUseCase,
    private val searchRecipesByCategoryUseCase: SearchRecipesByCategoryUseCase
) : ViewModel() {
    private val _randomRecipes = MutableLiveData<List<RecipeShort>>()
    val randomRecipes: LiveData<List<RecipeShort>> = _randomRecipes

    private val _searchRecipesByIngredients = MutableLiveData<List<RecipeShort>>()
    val searchRecipesByIngredients: LiveData<List<RecipeShort>> = _searchRecipesByIngredients

    private val _searchRecipesByCategoryIngredients = MutableLiveData<List<RecipeShort>>()
    val searchRecipesByCategoryIngredients: LiveData<List<RecipeShort>> = _searchRecipesByCategoryIngredients

    private val _searchRecipesByCategory = MutableLiveData<List<RecipeShort>>()
    val searchRecipesByCategory: LiveData<List<RecipeShort>> = _searchRecipesByCategory

    private val _ingredients = MutableLiveData<List<String>>(emptyList())
    val ingredients: LiveData<List<String>> = _ingredients

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> = _categories

    private val _selectedCategory = MutableLiveData<Categories?>()
    val selectedCategory: LiveData<Categories?> = _selectedCategory

    private val _recipesResult = MutableLiveData<List<RecipeShort>>()
    val recipesResult: LiveData<List<RecipeShort>> = _recipesResult

    private var categoriesLoaded = false

    fun fetchCategoriesOnce() {
        if (!categoriesLoaded) {
            categoriesLoaded = true
            viewModelScope.launch {
                _categories.value = getCategoriesUseCase()
            }

        }
    }


    fun fetchRandomRecipe() {
        viewModelScope.launch {
            //_randomRecipes.value = getRandomRecipesUseCase()
            val random = getRandomRecipesUseCase()
            _randomRecipes.value = random
            _recipesResult.value = random
        }
        //search()

    }


    fun addCategory(categories: Categories) {
        //_selectedCategory.value = categories
        //val current = _selectedCategory.value
        if (categories == selectedCategory.value) {
            _selectedCategory.value = null
        } else {
            _selectedCategory.value = categories
        }
        search()
    }


fun addIngredient(ingredient: String) {
    val current = _ingredients.value.orEmpty()
    _ingredients.value = if (ingredient in current) {
        current - ingredient
    } else {
        current + ingredient
    }
    search()
}

    fun removeIngredient(ingredient: String) {
        val updated = _ingredients.value.orEmpty().filterNot { it == ingredient }
        _ingredients.value = updated
        search()
    }

    fun onIngredientTextInput(input: String) {
        val parsedIngredients = input
            .split(",", ";")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        _ingredients.value = parsedIngredients
        search()
    }

    private fun search() {
        val currentIngredients = _ingredients.value.orEmpty()
        val currentCategory = _selectedCategory.value
        Log.d("currentIngredients",currentIngredients.toString())
        Log.d("currentCategory",currentCategory.toString())

        viewModelScope.launch {
            _recipesResult.value = when {
                currentIngredients.isNotEmpty() && currentCategory != null ->
                    searchRecipesByCategoryAndIngredientsUseCase(currentCategory.strCategory, currentIngredients)

                currentIngredients.isNotEmpty() ->
                    searchRecipesByIngredientsUseCase(currentIngredients)

                currentCategory != null ->
                    searchRecipesByCategoryUseCase(currentCategory.strCategory)
                else -> _randomRecipes.value //getRandomRecipesUseCase()
            }
        }
    }

    fun searchByIngredients(ingredients: List<String>) {
        viewModelScope.launch {
            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
        }
    }
    fun searchByCategory(input: String) {
        viewModelScope.launch {
            _searchRecipesByCategory.value = searchRecipesByCategoryUseCase(input)
        }
    }
    fun searchByIngredientsAndCategory(ingredients: List<String>) {
        viewModelScope.launch {
            _searchRecipesByCategoryIngredients.value = selectedCategory.value?.let {
                searchRecipesByCategoryAndIngredientsUseCase(
                    it.strCategory,ingredients)
            }
        }
    }
    fun searchByIngredients(input: String) {
        val ingredients = input.split(",", ";").map { it.trim() }.filter { it.isNotEmpty() }
        viewModelScope.launch {
            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
        }
    }
}