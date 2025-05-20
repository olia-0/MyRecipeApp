package com.example.myrecipeapp.ui.screens.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.usecase.GetSavedRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase
): ViewModel() {
    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes: StateFlow<List<Recipe>> = _savedRecipes.asStateFlow()

    init {
        fetchSavedRecipes()
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