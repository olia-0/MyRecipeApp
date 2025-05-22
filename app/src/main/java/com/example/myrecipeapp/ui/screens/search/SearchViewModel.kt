package com.example.myrecipeapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.GetViewedRecipesUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    getViewedRecipesUseCase: GetViewedRecipesUseCase,
    searchRecipesByNameUseCase: SearchRecipesByNameUseCase
): ViewModel(){
    val viewedRecipes = getViewedRecipesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _recipes = MutableStateFlow<List<RecipeShort>>(emptyList())
    val recipes: StateFlow<List<RecipeShort>> = _recipes.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)              // Затримка 300мс після останнього введеного символу
                .filter { it.isNotBlank() } // Ігноруємо порожні рядки
                .distinctUntilChanged()     // Ігноруємо однакові запити
                .collectLatest { query ->
                    try {
                        val results = searchRecipesByNameUseCase(query)
                        _recipes.value = results
                    } catch (e: Exception) {
                        _recipes.value = emptyList()
                        // Можна додати логування чи показ помилки
                    }
                }
//            _searchQuery
//                .collectLatest { query ->
//                    if (query.isNotBlank()) {
//                        val results = searchRecipesByNameUseCase(query)
//                        _recipes.value = results
//                    } else {
//                        _recipes.value = emptyList()
//                    }
//                }

        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}