package com.example.myrecipeapp.ui.screens.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.usecase.DeleteRecipeUseCase
import com.example.myrecipeapp.domain.usecase.GetSavedRecipesSizeUseCase
import com.example.myrecipeapp.domain.usecase.GetSavedRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
    private val getSavedRecipesSizeUseCase: GetSavedRecipesSizeUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
): ViewModel() {
    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes: StateFlow<List<Recipe>> = _savedRecipes.asStateFlow()

    //private val _memoryUsage = MutableLiveData<Pair<Long, Long>>()
    //val memoryUsage: LiveData<Pair<Long, Long>> = _memoryUsage
    private val _memoryUsage = MutableStateFlow<Pair<Long, Long>?>(null)
    val memoryUsage: StateFlow<Pair<Long, Long>?> = _memoryUsage.asStateFlow()

    init {
        fetchSavedRecipes()
        fetchSavedMemoryUsage()

    }
    //@Inject
    //lateinit var getSavedRecipesSizeUseCase: GetSavedRecipesSizeUseCase

    fun fetchSavedMemoryUsage() {
        viewModelScope.launch {
            while (true) {
                _memoryUsage.value = getSavedRecipesSizeUseCase()
                delay(2000)                                   ////Видали це повторення
            }
        }

    }
    fun deleteSavedRecipe(id: String) {
        viewModelScope.launch {
            deleteRecipeUseCase(id)
            fetchSavedMemoryUsage()
        }
    }
    private fun fetchSavedRecipes() {
        viewModelScope.launch {
            getSavedRecipesUseCase().collect { recipes ->
                _savedRecipes.value = recipes
            }
        }
    }

//    private fun fetchSavedRecipes() {
//        viewModelScope.launch {
//            val recipes = getSavedRecipesUseCase()
//            _savedRecipes.value = recipes
//        }
//    }
}