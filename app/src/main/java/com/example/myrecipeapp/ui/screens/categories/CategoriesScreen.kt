package com.example.myrecipeapp.ui.screens.categories

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myrecipeapp.R
import com.example.myrecipeapp.data.local.Category
import com.example.myrecipeapp.data.local.categoryList
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.ui.components.BackButton
import com.example.myrecipeapp.ui.components.SearchButton
import com.example.myrecipeapp.ui.screens.home.HomeViewModel
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White

@Composable
fun CategoriesScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
){
    val categoriesRecipes by homeViewModel.categories.observeAsState()
    //val recipesByCategory by homeViewModel.searchRecipesByCategory.observeAsState()
    val selectedCategory by homeViewModel.selectedCategory.observeAsState()
    LaunchedEffect(Unit) {
        homeViewModel.fetchCategoriesOnce()
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
        item(){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(15.dp),
                modifier = Modifier
                    .fillParentMaxSize()
            ) {
                categoriesRecipes?.let {
                    items(it.size){ i ->
                        RecipeCategoryCardComponent(navController,it[i])
                    }
                }
            }
        }
    }


}


@Composable
fun RecipeCategoryCardComponent(
    navController: NavHostController,
    category: Categories,

){

    Card(
        modifier = Modifier
            .padding(10.dp)
            .width(80.dp)
            .height(130.dp),
        onClick = { navController.navigate("category")}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ){
            Image(
                painter = painterResource(R.drawable.beef),//painterResource(category.strCategory),
                contentDescription = stringResource(R.string.beef_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ){
                Text(
                    category.strCategory,
                    fontSize = 18.sp,
                    color = White
                )
                //Spacer(modifier = Modifier.size(2.dp))
//                Text(
//                    text = recipesByCategory?.size.toString()+" recipes",
//                    fontSize = 12.sp,
//                    color = White
//                )
            }
        }
    }
}




