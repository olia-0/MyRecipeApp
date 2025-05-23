package com.example.myrecipeapp.ui.screens.category

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.navigation.AppRoute
import com.example.myrecipeapp.ui.components.BackButton
import com.example.myrecipeapp.ui.components.SearchButton
import com.example.myrecipeapp.ui.screens.saved.CardRecipeCategorySaved
import com.example.myrecipeapp.ui.theme.Gray300
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate400
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White
import kotlinx.coroutines.launch

//@Preview(showSystemUi = true)
@Composable
fun CategoryScreen(
    navController: NavHostController,
    categoryName: String,
    viewModel: CategoryViewModel = hiltViewModel()
){
    val category by viewModel.category.observeAsState()
    val categoriesRecipes by viewModel.searchRecipesByCategory.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getCategoryByName(categoryName)
        if (categoriesRecipes.isNullOrEmpty()) {
            viewModel.searchByCategory(categoryName)
        }

    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Row(modifier = Modifier
                //.padding(5.dp,0.dp)
                .fillParentMaxWidth()
                .height(60.dp)
                .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BackButton(navController)
                Text(
                    text = "Recipe Categories",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )
                SearchButton(navController)
            }

        }
        item {
            category?.let { RecipeCategoriesFull(it) }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),//.padding(top = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                categoriesRecipes?.chunked(2)?.forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        rowItems.forEach { recipe ->
                            var isSaved by rememberSaveable { mutableStateOf(false) }
                            LaunchedEffect(Unit) {
                                isSaved = viewModel.isRecipeSaved(recipe.id)
                            }
                            Box(modifier = Modifier.padding(5.dp)) {
                                CardRecipeCategorySaved(
                                    navController,
                                    recipe.toRecipe(),
                                    coroutineScope,
                                    onClick = {
                                        coroutineScope.launch {
                                            if (isSaved) {
                                                viewModel.deleteSavedRecipe(recipe.id)
                                                isSaved = false
                                            } else {
                                                viewModel.saveRecipeWithImage(context, recipe.toRecipe())
                                                Log.d("Кнопка збереження - не збережено", recipe.name)
                                                isSaved = true
                                            }
                                        }
                                    },isSaved)
                            }
                        }
                        }
                        // Якщо непарна кількість — додай порожній блок для вирівнювання
                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }

    }




    ///////////////////////////////////////
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        item {
//            Row(modifier = Modifier
//                //.padding(5.dp,0.dp)
//                .fillParentMaxWidth()
//                .height(60.dp)
//                .padding(15.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                BackButton(navController)
//                Text(
//                    text = "Recipe Categories",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Slate900
//                )
//                SearchButton(navController)
//            }
//
//        }
//        item {
//            category?.let { RecipeCategoriesFull(it) }
//        }
//        item(){
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                contentPadding = PaddingValues(15.dp,),
//                modifier = Modifier.fillParentMaxSize()//.padding(8.dp)
//            ) {
//                categoriesRecipes?.let {
//                    items(it.size){index ->
//                        //CardRecipeCategory(navController,it[index])
//                        var isSaved by rememberSaveable { mutableStateOf(false) }
//                        LaunchedEffect(Unit) {
//                            isSaved = viewModel.isRecipeSaved(it[index].id)
//                        }
//                        Box(modifier = Modifier.padding(5.dp)) {
//                            CardRecipeCategorySaved(
//                                navController,
//                                it[index].toRecipe(),
//                                coroutineScope,
//                                onClick = {
//                                    coroutineScope.launch {
//                                        if (isSaved) {
//                                            viewModel.deleteSavedRecipe(it[index].id)
//                                            isSaved = false
//                                        } else {
//                                            viewModel.saveRecipeWithImage(context, it[index].toRecipe())
//                                            Log.d("Кнопка збереження - не збережено", it[index].name)
//                                            isSaved = true
//                                        }
//                                    }
//                                },isSaved)
//                        }
//                    }
//                }
//            }
//        }
//    }




@Composable
fun CardRecipeCategory(
    navController: NavHostController,
    recipeShort: RecipeShort
){
    Card(modifier = Modifier
        .width(200.dp)
        //.fillMaxWidth()
        .height(200.dp)
        .padding(10.dp)
        .shadow(
            elevation = 10.dp, // Висота тіні
            shape = RoundedCornerShape(12.dp), // Форма тіні
            ambientColor = Gray400, // Колір основної тіні
            spotColor = Gray300 // Колір блиску (spot) тіні
        ),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = MyPrimeryOrang,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = {
            navController.navigate(AppRoute.recipeWithId(recipeShort.id))
        }
    ) {
        var isSave by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            AsyncImage(
                model = recipeShort.thumbnail,
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = recipeShort.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900,
                //lineHeight = 16.sp,
                modifier = Modifier.padding(8.dp,0.dp)
            )
            //Spacer(modifier = Modifier.size(5.dp))
            Row(
                modifier = Modifier.padding(10.dp,5.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Text(
//                    text = recipeShort,
//                    color = Slate400,
//                    fontSize = 11.sp
//                )
                //Spacer(modifier = Modifier.weight(10.dp))
                Icon(
                    imageVector = if(isSave) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.icon_save),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { isSave = !isSave }

                )
            }
        }

    }
}
@Composable
fun RecipeCategoriesFull(
    //navController: NavHostController,
    category: Category
){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .height(150.dp),
        //onClick = { navController.navigate("category")}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
//            Image(
//                painter = painterResource(category.imageResId),
//                contentDescription = stringResource(R.string.beef_image),
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxSize()
//            )
            AsyncImage(
                model = category.imagePath,
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .fillMaxSize(),
                //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ){
                Text(
                    category.name,
                    fontSize = 22.sp,
                    color = White
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    "16000 recipes",
                    fontSize = 12.sp,
                    color = White
                )
            }
        }
    }
}

