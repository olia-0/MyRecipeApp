package com.example.myrecipeapp.ui.screens.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myrecipeapp.data.mapper.toRecipeShort
import com.example.myrecipeapp.ui.components.SearchRecipeByNameTextField
import com.example.myrecipeapp.ui.components.SearchRecipeTextField
import com.example.myrecipeapp.ui.components.SearchTextField
import com.example.myrecipeapp.ui.screens.home.CardRecipeHomeScreen
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
fun SearchScreen(
    navController: NavHostController = rememberNavController(),
    viewModel: SearchViewModel = hiltViewModel()
) {
    val viewedRecipes by viewModel.viewedRecipes.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val recipes by viewModel.recipes.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isSaved by rememberSaveable { mutableStateOf(true) }
    //Text("SearchScreen")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
            //.padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.size(20.dp))
            //SearchTextField(modifier = Modifier)
            //SearchRecipeTextField()
            SearchRecipeByNameTextField(
                searchNameRecipe = {
                    nameRecipe -> viewModel.onSearchQueryChanged(nameRecipe)
                }
            )
        }

//        item {
//            Row(
//                modifier = Modifier
//                    .padding(top = 20.dp, bottom = 20.dp)
//                    .fillParentMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Search history",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Slate900,
//                    modifier = Modifier.padding(8.dp)
//                )
//                Text(
//                    text = "See All",
//                    fontSize = 18.sp,
//                    //fontWeight = FontWeight.Bold,
//                    color = MyPrimeryOrang,
//                    modifier = Modifier.padding(8.dp)
//                )
//            }
//        }
//        item {
//            Column {
//                repeat(3){
//                    HistoryCardRecipe()
//                }
//            }
//        }
        item {
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Last Viewed",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "See All",
                    fontSize = 18.sp,
                    //fontWeight = FontWeight.Bold,
                    color = MyPrimeryOrang,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(300.dp),
                //.background(Color.Cyan),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                items(viewedRecipes.size){index ->
                    var isSaved by rememberSaveable { mutableStateOf(false) }
                    LaunchedEffect(Unit) {
                        isSaved = viewModel.isRecipeSaved(viewedRecipes[index].idRecipe)
                    }
                    CardRecipeHomeScreen(
                        navController,
                        viewedRecipes[index].toRecipeShort(),
                        //viewModel
                        onClick = {
                            coroutineScope.launch {
                                if (viewModel.isRecipeSaved(viewedRecipes[index].idRecipe)) {
                                    viewModel.deleteSavedRecipe(viewedRecipes[index].idRecipe)
                                    isSaved = false
                                } else {
                                    val recipe = viewModel.fetchMealById(viewedRecipes[index].idRecipe)
                                    viewModel.saveRecipeWithImage(context, recipe)
                                    isSaved = true
                                }
                            }
                        },isSaved

//                        onClick = {
//                            coroutineScope.launch {
//                                if (viewModel.isRecipeSaved(viewedRecipes[index].idRecipe)) {
//                                    viewModel.deleteSavedRecipe(viewedRecipes[index].idRecipe)
//                                    //isSaved = false
//                                } else {
//                                    viewModel.saveRecipeWithImage(context, viewedRecipes[index])
//                                    //isSaved = true
//                                }
//                            }
//                        }
                    )
                    //Text("1")
                }
            }
        }
//        items(recipes) { recipe ->
//            Text(text = recipe.nameRecipe)
//            Divider()
//        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),//.padding(top = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                recipes.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        rowItems.forEach { recipe ->
                            var isSaved by rememberSaveable { mutableStateOf(false) }
                            LaunchedEffect(Unit) {
                                isSaved = viewModel.isRecipeSaved(recipe.idRecipe)
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                CardRecipeCategorySaved(
                                    navController,
                                    recipe,
                                    coroutineScope,
                                    onClick = {
                                        coroutineScope.launch {
                                            if (viewModel.isRecipeSaved(recipe.idRecipe)) {
                                                viewModel.deleteSavedRecipe(recipe.idRecipe)
                                                isSaved = false
                                            } else {
                                                viewModel.saveRecipeWithImage(context, recipe)
                                                Log.d("Кнопка збереження - не збережено", recipe.nameRecipe)
                                                isSaved = true
                                            }
                                        }
                                    },isSaved)
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
}
@Composable
fun HistoryCardRecipe(){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(3.dp)
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
        shape = RoundedCornerShape(12.dp)

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            AsyncImage(
                model = "https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg",
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .size(80.dp),
                //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = "Recipe f fff sgdfg gfgdh dgfdgdf gf 1",
                    fontSize = 14.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "2 days ago",
                    fontSize = 12.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Slate400,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}