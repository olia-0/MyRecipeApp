package com.example.myrecipeapp.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.usecase.GetViewedRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    getViewedRecipesUseCase: GetViewedRecipesUseCase
): ViewModel(){
    val viewedRecipes = getViewedRecipesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )
}