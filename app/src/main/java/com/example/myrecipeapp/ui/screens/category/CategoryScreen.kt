package com.example.myrecipeapp.ui.screens.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.data.local.Category
import com.example.myrecipeapp.ui.components.BackButton
import com.example.myrecipeapp.ui.components.SearchButton
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray300
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate400
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White

//@Preview(showSystemUi = true)
@Composable
fun CategoryScreen(
    navController: NavHostController
){
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
            RecipeCategoriesFull()
        }
        item(){
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(5.dp,),
                modifier = Modifier.fillParentMaxSize()//.padding(8.dp)
            ) {
                items(14){
                    CardRecipeCategory(navController)
                }
            }
        }
    }


}

@Composable
fun CardRecipeCategory(
    navController: NavHostController
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
        onClick = {}
    ) {
        var isSave by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            AsyncImage(
                model = "https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg",
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = "Chilli prawn linguine",
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
                Text(
                    text = "Italian",
                    color = Slate400,
                    fontSize = 11.sp
                )
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
    //category: Category
){
    Card(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
            .height(120.dp),
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
                model = "https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg",
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
                    //category.name,
                    "Beef",
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

