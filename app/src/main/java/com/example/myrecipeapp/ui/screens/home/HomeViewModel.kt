package com.example.myrecipeapp.ui.screens.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.data.mapper.toSavedRecipeEntity
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.domain.usecase.DeleteRecipeUseCase
import com.example.myrecipeapp.domain.usecase.DownloadRecipeImageUseCase
import com.example.myrecipeapp.domain.usecase.GetCategoriesUseCase
import com.example.myrecipeapp.domain.usecase.GetRandomRecipesUseCase
import com.example.myrecipeapp.domain.usecase.GetRecipeByIdWithFallbackUseCase
import com.example.myrecipeapp.domain.usecase.IsRecipeSavedUseCase
import com.example.myrecipeapp.domain.usecase.SaveRecipeUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByCategoryAndIngredientsUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByCategoryUseCase
import com.example.myrecipeapp.domain.usecase.SearchRecipesByIngredientsUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomRecipesUseCase: GetRandomRecipesUseCase,
    private val searchRecipesByIngredientsUseCase: SearchRecipesByIngredientsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchRecipesByCategoryAndIngredientsUseCase: SearchRecipesByCategoryAndIngredientsUseCase,
    private val searchRecipesByCategoryUseCase: SearchRecipesByCategoryUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    private val isRecipeSavedUseCase: IsRecipeSavedUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val downloadImageUseCase: DownloadRecipeImageUseCase,
    private val getRecipeByIdWithFallbackUseCase: GetRecipeByIdWithFallbackUseCase,
    private val userPreferences: UserPreferences
    //private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }
    var currentUser by mutableStateOf(auth.currentUser)
        private set

    val username: StateFlow<String> = userPreferences.userProfile
        .map { it.username }
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

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

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _selectedCategory = MutableLiveData<Category?>()
    val selectedCategory: LiveData<Category?> = _selectedCategory

    private val _recipesResult = MutableLiveData<List<RecipeShort>>()
    val recipesResult: LiveData<List<RecipeShort>> = _recipesResult

    private var categoriesLoaded = false
    init {
        Log.d("Я тут 1","")
        fetchCategoriesOnce()
        fetchRandomRecipe()
        currentUser = auth.currentUser

    }
    fun signOut() {
        auth.signOut()
        currentUser = null
        viewModelScope.launch {
            userPreferences.clear()
        }
    }

    fun fetchCategoriesOnce() {
        Log.d("Я тут 2","")
        if (!categoriesLoaded) {
            Log.d("Я тут 2_1","")
            categoriesLoaded = true
            viewModelScope.launch {
                Log.d("Я тут 2_2","")
                _categories.value = getCategoriesUseCase()
                Log.d("Я тут 2_3","")
            }
            Log.d("Я тут 2_4","")

        }
        Log.d("Я тут 2_5","")
    }


    fun fetchRandomRecipe() {
        viewModelScope.launch {
            //_randomRecipes.value = getRandomRecipesUseCase()
            Log.d("Я тут 2_6", "")
            val random = getRandomRecipesUseCase()
            _randomRecipes.value = random
            Log.d("рецепт",random.toString())
            _recipesResult.value = random
        }
        //search()

    }


    fun addCategory(categories: Category) {
        Log.d("Я тут 3","")
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
    Log.d("Я тут 4","")
    val current = _ingredients.value.orEmpty()
    _ingredients.value = if (ingredient in current) {
        current - ingredient
    } else {
        current + ingredient
    }
    _ingredients.value?.let { Log.d("Інгредієнти", it.toString()) }
    search()
}

    fun removeIngredient(ingredient: String) {
        Log.d("Я тут 5","")
        val updated = _ingredients.value.orEmpty().filterNot { it == ingredient }
        _ingredients.value = updated
        search()
    }

    fun onIngredientTextInput(input: String) {
        Log.d("Я тут 6","")
        val parsedIngredients = input
            .split(",", ";")
            .map { it.trim() }
            .filter { it.isNotEmpty() }

        _ingredients.value = parsedIngredients
        search()
    }

    private fun search() {
        Log.d("Я тут 7","")
        val currentIngredients = _ingredients.value.orEmpty()
        val currentCategory = _selectedCategory.value
        Log.d("currentIngredients",currentIngredients.toString())
        Log.d("currentCategory",currentCategory.toString())

        viewModelScope.launch {
            _recipesResult.value = when {
                currentIngredients.isNotEmpty() && currentCategory != null -> {
                    Log.d("Case1",currentIngredients.toString()+currentCategory.toString())
                    searchRecipesByCategoryAndIngredientsUseCase(currentCategory.name, currentIngredients)}

                currentIngredients.isNotEmpty() ->{
                    Log.d("Case2",currentIngredients.toString())
                    searchRecipesByIngredientsUseCase(currentIngredients)}

                currentCategory != null ->{
                    Log.d("Case3",currentCategory.toString())
                    searchRecipesByCategoryUseCase(currentCategory.name)}
                else -> _randomRecipes.value //getRandomRecipesUseCase()
            }
        }
    }
    ////
    suspend fun isRecipeSaved(id: String): Boolean = withContext(Dispatchers.IO) {
        isRecipeSavedUseCase(id)
    }

    fun deleteSavedRecipe(id: String) {
        viewModelScope.launch {
            deleteRecipeUseCase(id)
            //fetchSavedMemoryUsage()
        }
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
    suspend fun fetchMealById(id: String) : Recipe{
        return getRecipeByIdWithFallbackUseCase(id)
    }

//    fun searchByIngredients(ingredients: List<String>) {
//        viewModelScope.launch {
//            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
//        }
//    }
//    fun searchByCategory(input: String) {
//        viewModelScope.launch {
//            _searchRecipesByCategory.value = searchRecipesByCategoryUseCase(input)
//        }
//    }
//    fun searchByIngredientsAndCategory(ingredients: List<String>) {
//        viewModelScope.launch {
//            _searchRecipesByCategoryIngredients.value = selectedCategory.value?.let {
//                searchRecipesByCategoryAndIngredientsUseCase(
//                    it.strCategory,ingredients)
//            }
//        }
//    }
//    fun searchByIngredients(input: String) {
//        val ingredients = input.split(",", ";").map { it.trim() }.filter { it.isNotEmpty() }
//        viewModelScope.launch {
//            _searchRecipesByIngredients.value = searchRecipesByIngredientsUseCase(ingredients)
//        }
//    }
}