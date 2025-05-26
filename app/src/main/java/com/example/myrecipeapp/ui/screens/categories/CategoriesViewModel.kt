package com.example.myrecipeapp.ui.screens.categories

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.usecase.AddCategoryUseCase
import com.example.myrecipeapp.domain.usecase.ClearAllCategoriesUseCase
import com.example.myrecipeapp.domain.usecase.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val clearAllCategoriesUseCase: ClearAllCategoriesUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val categories = mutableStateListOf<Category>()

    init {
        //clearCategory()
        Log.e("AddCategory", "Помилка")
        viewModelScope.launch {
            //val list = getCategoriesUseCase()
            //categories.addAll(list)
//            addCategoryUseCase(
//                context,"Beef","https://img.freepik.com/free-photo/front-view-big-meat-slice-raw-meat-with-pepper-greens-dark-photo-chicken-meal-animal-barbecue-food-butcher_179666-43725.jpg"
//            )
//            addCategoryUseCase(
//                context,"Chicken","https://img.freepik.com/free-photo/high-angle-roasted-chicken-plate-thanksgiving-dinner_23-2148638971.jpg"
//            )
//            addCategoryUseCase(
//                context,"Dessert","https://img.freepik.com/free-photo/orange-cake-decorated-with-fresh-orange-slices-mimosa-flowers-light_114579-7720.jpg"
//            )
//            addCategoryUseCase(
//                context,"Goat","https://img.freepik.com/free-photo/closeup-cooked-beef-with-spices-fried-green-red-peppers-with-blurred-background_181624-2210.jpg"
//            )
//            addCategoryUseCase(
//                context,"Lamb","https://img.freepik.com/free-photo/grilled-lamb-ribs-close-up_23-2148516959.jpg"
//            )
//            addCategoryUseCase(
//                context,"Miscellaneous","https://img.freepik.com/free-photo/flat-lay-table-full-delicious-food-arrangement_23-2149141378.jpg"
//            )
//            addCategoryUseCase(
//                context,"Pasta","https://img.freepik.com/free-photo/penne-pasta-tomato-sauce-with-chicken-tomatoes-wooden-table_2829-19739.jpg"
//            )
//            addCategoryUseCase(
//                context,"Pork","https://img.freepik.com/free-photo/high-angle-steak-with-salad-beer_23-2148754954.jpg"
//            )
//            addCategoryUseCase(
//                context,"Seafood","https://img.freepik.com/free-photo/flat-lay-pan-with-mussels-white-sauce_23-2148234893.jpg"
//            )
//            addCategoryUseCase(
//                context,"Side","https://img.freepik.com/free-photo/delicious-high-protein-vegan-meal_23-2149039365.jpg"
//            )
//            addCategoryUseCase(
//                context,"Starter","https://img.freepik.com/free-photo/white-plate-with-fried-eggs-orange-juice_23-2148341673.jpg"
//            )
//            addCategoryUseCase(
//                context,"Vegan","https://img.freepik.com/free-photo/healthy-vegan-lunch-bowl-buddha-bowl-salad-healthy-balanced-vegetarian-food-concept_1150-37834.jpg"
//            )
//            addCategoryUseCase(
//                context,"Vegetarian","https://img.freepik.com/premium-photo/high-angle-view-food-plate-table_1048944-28960889.jpg"
//            )


//            addCategoryUseCase(
//                context,"Другі страви","https://img.freepik.com/free-photo/pasta-shape-heart-salad-with-tomatoes-cucumbers-olives-mozzarella-red-onion-greek-style_2829-14032.jpg")
//            addCategoryUseCase(
//                context,"Перші страви","https://img.freepik.com/free-photo/high-angle-winter-vegetables-soup-bowl-with-spoon-toast_23-2148706346.jpg")
//            addCategoryUseCase(
//                context,"Салати","https://img.freepik.com/premium-photo/high-angle-view-food-plate-table_1048944-28960889.jpg")
//            addCategoryUseCase(
//                context,"Закуски","https://img.freepik.com/free-photo/white-plate-with-fried-eggs-orange-juice_23-2148341673.jpg")
//            addCategoryUseCase(
//                context,"Напої","https://img.freepik.com/free-photo/side-view-organic-fresh-juices-bottles-served-with-tubes-fruits-wooden-cutting-board_140725-94665.jpg")
//            addCategoryUseCase(
//                context,"Десерти","https://img.freepik.com/free-photo/orange-cake-decorated-with-fresh-orange-slices-mimosa-flowers-light_114579-7720.jpg")
//            addCategoryUseCase(
//                context,"Випічка","https://img.freepik.com/free-photo/fresh-danish-bread-with-milk-fruit-blueberry-cherry-sauce-served-with-milk_1150-23544.jpg")
//            addCategoryUseCase(
//                context,"Заготівлі","https://img.freepik.com/free-photo/jars-with-preserved-food-assortment_23-2149239013.jpg")


//            addCategoryUseCase(
//                context,"Другі страви","")

        }
    }
    fun clearCategory(){
        viewModelScope.launch {
            clearAllCategoriesUseCase()
        }
    }

    fun addCategory(name: String, imageUrl: String) {
        viewModelScope.launch {
            try {
                addCategoryUseCase(context, name, imageUrl)
                categories.clear()
                categories.addAll(getCategoriesUseCase())
            } catch (e: Exception) {
                Log.e("AddCategory", "Помилка: ${e.message}")
            }
        }
    }
}
