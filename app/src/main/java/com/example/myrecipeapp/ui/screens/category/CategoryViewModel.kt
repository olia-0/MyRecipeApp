package com.example.myrecipeapp.ui.screens.category

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.mapper.toSavedRecipeEntity
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.AddCategoryUseCase
import com.example.myrecipeapp.domain.usecase.DeleteRecipeUseCase
import com.example.myrecipeapp.domain.usecase.DownloadRecipeImageUseCase
import com.example.myrecipeapp.domain.usecase.GetCategoriesUseCase
import com.example.myrecipeapp.domain.usecase.GetCategoryByNameUseCase
import com.example.myrecipeapp.domain.usecase.GetRecipeByIdWithFallbackUseCase
import com.example.myrecipeapp.domain.usecase.IsRecipeSavedUseCase
import com.example.myrecipeapp.domain.usecase.SaveRecipeUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategoryByNameUseCase: GetCategoryByNameUseCase,
    private val searchRecipesByCategoryUseCase: SearchRecipesByCategoryUseCase,
    private val isRecipeSavedUseCase: IsRecipeSavedUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val downloadImageUseCase: DownloadRecipeImageUseCase,
    private val getRecipeByIdWithFallbackUseCase: GetRecipeByIdWithFallbackUseCase
) : ViewModel() {

    //val category = mutableStateListOf<Category>()
    private val _category = MutableLiveData<Category>()
    val category: LiveData<Category> = _category

    private val _searchRecipesByCategory = MutableLiveData<List<RecipeShort>>()
    val searchRecipesByCategory: LiveData<List<RecipeShort>> = _searchRecipesByCategory

    init {

    }
    fun getCategoryByName(name: String){
        viewModelScope.launch {
            _category.value = getCategoryByNameUseCase(name)
        }
    }
    fun searchByCategory(input: String) {
        viewModelScope.launch {
            _searchRecipesByCategory.value = searchRecipesByCategoryUseCase(input)
        }
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
                val savedEntity = imagePath?.let { recipe }
                savedEntity?.let { saveRecipeUseCase(context,it,recipe.photoRecipe ?: "") }
            }catch (e: Exception){
                Log.d("Помилка в збережені фото рецепту", e.toString())
            }

        }
    }



}
