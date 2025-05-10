package com.example.myrecipeapp.ui.screens.recipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.components.BackButton
import com.example.myrecipeapp.ui.components.SavedButton
import com.example.myrecipeapp.ui.components.SearchButton
import com.example.myrecipeapp.ui.theme.Slate900

//@Preview(showSystemUi = true)
@Composable
fun RecipeScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    val meals by viewModel.selectedMeal.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMealById("52772")
        meals?.let { Log.d("AAAAAA Name Recipe", it.nameRecipe) }
    }
    Text(
        text = meals?.nameRecipe ?: "Ooops",
        fontSize = 22.sp
    )

    LazyColumn {
        item {
            Box() {
                AsyncImage(
                    model = "https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg",
                    contentDescription = "Зображення з Інтернету",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    //.clip(CircleShape), // Обрізає у круглу форму
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        //.padding(5.dp,0.dp)
                        .fillParentMaxWidth()
                        .height(100.dp)
                        .padding(15.dp, 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BackButton(navController)
                    Text(
                        text = meals?.nameRecipe ?: "Не знайдено рецепт",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    SavedButton()
                }



            }
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun SavedButton() {
//    Box(
//        modifier = Modifier
//            .size(50.dp)
//            .clip(RoundedCornerShape(25.dp))
//            .background(Color.Cyan),
//        contentAlignment = Alignment.Center
//    ){
//        IconButton(
//            modifier = Modifier
//                .size(40.dp),
//            onClick = {
//                //navController.popBackStack()
//            }
//        ) {
//            Icon(
//                imageVector = Icons.Default.Favorite,
//                contentDescription = stringResource(R.string.icon_button_back),
//                modifier = Modifier
//                    .fillMaxSize()
//            )
//        }
//    }
//}