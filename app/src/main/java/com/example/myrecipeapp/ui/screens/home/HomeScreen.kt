package com.example.myrecipeapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray200
import com.example.myrecipeapp.ui.theme.Gray300
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate400
import com.example.myrecipeapp.ui.theme.Slate600
import com.example.myrecipeapp.ui.theme.Slate700
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),

    ) {
        val countRecipe = 10
        item {
            Row(modifier = Modifier
                .padding(20.dp)
                .fillParentMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.img1),
                        contentDescription = stringResource(id = R.string.image_user),
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                            .size(50.dp)

                    )
                    VerticalDivider(modifier = Modifier.padding(5.dp))
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,

                    ) {
                        Text(
                            text = "Welcome,👋🏻",
                            fontSize = 14.sp,
                            color = Gray400,
                            lineHeight = 14.sp
                        )

                        Text(text = "Ola",
                            fontSize = 16.sp,
                            color = Slate900,
                            lineHeight = 19.sp
                        )
                    }
                }
                //VerticalDivider(modifier = Modifier.padding(20.dp))
                CategoryButton(navController)
//                Image(
//                    painter = painterResource(id = R.drawable.notification),
//                    contentDescription = stringResource(id = R.string.home_button),
//                    modifier = Modifier.size(40.dp)
//                )
            }
        }
        item {
            Box(modifier = Modifier
                .fillParentMaxWidth()
                .padding(25.dp,10.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Gray100)){
                Row(modifier = Modifier
                    .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    BorderlessTextField(modifier = Modifier)

                    //VerticalDivider(modifier = Modifier.size(8.dp))
                    Spacer(modifier = Modifier.size(8.dp))
                    Icon(modifier = Modifier.size(40.dp).padding(5.dp),
                        painter = painterResource(R.drawable.search),
                        contentDescription = stringResource(R.string.home_search_ingredient),
                        tint = Gray400
                    )
                }
            }
        }
        item {
            CardFridge()
//            LazyHorizontalGrid(
//                rows = GridCells.Fixed(2), // 2 стовпці
//                contentPadding = PaddingValues(top = 8.dp),
//                modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.Cyan)
//            ) {
//               items(100) {
//                   Text(text = "Ola1",
//                       fontSize = 16.sp,
//                       color = Slate900,
//                       lineHeight = 19.sp
//                   )
//
//               }
//            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(350.dp),
                    //.background(Color.Cyan),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                items(countRecipe){
                    CardRecipeHomeScreen()
                }
            }
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(350.dp),
                //.background(Color.Cyan),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                items(countRecipe){
                    CardRecipeHomeScreen()
                }
            }
        }

    }
}
//@Preview(showSystemUi = true)
//@Preview(showBackground = true )
@Composable
fun CategoryButton(
    navController: NavHostController
){
    ElevatedButton(
        modifier = Modifier
            //.background(Gray400)
            //.padding(15.dp,5.dp)
            //.fillMaxWidth()
            .width(180.dp)
            .height(40.dp),
            //.background(Gray400),
        colors = ButtonDefaults.elevatedButtonColors(
            contentColor = Slate600,
            containerColor = Gray100
        ),
        onClick = {
            navController.navigate(route = "categories")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Category",
                fontSize = 14.sp,
                //fontWeight = FontWeight.Bold
            )
            //Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(R.string.category_button_icon),
                modifier = Modifier
                    .size(25.dp)

            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderlessTextField(modifier: Modifier) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Введіть текст...", color = Gray400,fontSize = 14.sp,lineHeight = 14.sp ) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            unfocusedTextColor = Gray400,
            focusedTextColor = Slate900

        ),
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            lineHeight = 14.sp
        )
    )
}
@Composable
fun CardFridge(){
    val ingredients = listOf(
        "Meat" to "🥩",
        "Chicken" to "🍗",
        "Fish" to "🐟",
        "Milk" to "🥛",
        "Flour" to "😶‍🌫️",
        "Sugar" to "🍪",
        "Egg" to "🥚",
        "Broccoli" to "🥦",
        "Potato" to "🥔"
    )
    Column {
        Text(text = "What's in your fridge?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Slate900,
            modifier = Modifier.padding(8.dp)
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2), // 2 стовпці
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.fillMaxWidth().height(100.dp)
        ) {
            items(ingredients.size) { ingredient ->
                IngredientCard(
                    ingredient = ingredients[ingredient].first,
                    imageRes = ingredients[ingredient].second)
            }
        }
    }
}
@Composable
fun IngredientCard(ingredient: String, imageRes: String) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .padding(2.dp,3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Gray100)
            .padding(4.dp)
    ) {
        Row (
            modifier = Modifier.padding(6.dp).align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = ingredient, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Gray400)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = imageRes, fontSize = 14.sp)
//            Image(
//                painter = painterResource(id = imageRes),
//                contentDescription = ingredient,
//                modifier = Modifier.size(30.dp)
//            )

        }
    }
}

@Composable
fun CardRecipeHomeScreen(){
    Card(modifier = Modifier
        .width(300.dp)
        .height(282.dp)
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
        shape = RoundedCornerShape(12.dp)
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
                    .height(160.dp),
                    //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Chilli prawn linguine",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900,
                modifier = Modifier.padding(8.dp)
                )
            //Spacer(modifier = Modifier.size(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp,10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Pasta, Italian",
                    color = Slate400,
                    fontSize = 12.sp
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