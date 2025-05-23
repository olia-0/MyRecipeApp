package com.example.myrecipeapp.ui.screens.search

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.mapper.toSavedRecipeEntity
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.DeleteRecipeUseCase
import com.example.myrecipeapp.domain.usecase.DownloadRecipeImageUseCase
import com.example.myrecipeapp.domain.usecase.GetRandomRecipesUseCase
import com.example.myrecipeapp.domain.usecase.GetRecipeByIdWithFallbackUseCase
import com.example.myrecipeapp.domain.usecase.GetViewedRecipesUseCase
import com.example.myrecipeapp.domain.usecase.IsRecipeSavedUseCase
import com.example.myrecipeapp.domain.usecase.SaveRecipeUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    getViewedRecipesUseCase: GetViewedRecipesUseCase,
    searchRecipesByNameUseCase: SearchRecipesByNameUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val isRecipeSavedUseCase: IsRecipeSavedUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val downloadImageUseCase: DownloadRecipeImageUseCase,
    private val getRecipeByIdWithFallbackUseCase: GetRecipeByIdWithFallbackUseCase,
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase
): ViewModel(){
    val viewedRecipes = getViewedRecipesUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes.asStateFlow()

    init {
        viewModelScope.launch {
            fetchRandomRecipe()
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
    fun fetchRandomRecipe() {
        viewModelScope.launch {
            //_randomRecipes.value = getRandomRecipesUseCase()
            Log.d("Я тут 2_6", "")
            val random = getRandomRecipesUseCase()
            _recipes.value = random.map { it.toRecipe() }
            //_recipesResult.value = random
        }
        //search()

    }


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    suspend fun isRecipeSaved(id: String): Boolean = withContext(Dispatchers.IO) {
        isRecipeSavedUseCase(id)
    }

    fun deleteSavedRecipe(id: String) {
        viewModelScope.launch {
            deleteRecipeUseCase(id)
            //fetchSavedMemoryUsage()
        }
    }
    suspend fun fetchMealById(id: String) : Recipe{
        return getRecipeByIdWithFallbackUseCase(id)
    }
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
}