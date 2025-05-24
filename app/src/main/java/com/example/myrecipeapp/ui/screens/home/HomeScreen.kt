package com.example.myrecipeapp.ui.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.RecipeShort
import com.example.myrecipeapp.navigation.AppRoute
import com.example.myrecipeapp.ui.components.SearchTextField
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
import kotlinx.coroutines.launch

//@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
//@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val recipesRandom10 by homeViewModel.randomRecipes.observeAsState()
    val recipesByIngredients by homeViewModel.searchRecipesByIngredients.observeAsState()
    val selectedIngredients by homeViewModel.ingredients.observeAsState(emptyList())
    val recipesByCategoryIngredients by homeViewModel.searchRecipesByCategoryIngredients.observeAsState()
    val categoriesRecipes by homeViewModel.categories.observeAsState()
    val selectedCategory by homeViewModel.selectedCategory.observeAsState()
    val user = homeViewModel.currentUser
//    LaunchedEffect(Unit) {
//        //homeViewModel.fetchRandomRecipe()
//        if (recipesRandom10.isNullOrEmpty()) {
//            homeViewModel.fetchRandomRecipe()
//        }
//        homeViewModel.fetchCategoriesOnce()
//        //homeViewModel.search()
//    }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val recipesToDisplay by homeViewModel.recipesResult.observeAsState()

    //val recipesToDisplay = recipesByCategoryIngredients ?: recipesByIngredients ?: recipesRandom10
    //Log.d("AAAA CARD", recipesToDisplay.toString())
    //Log.d("AAAA Ingredients", selectedIngredients.toString())
    //Log.d("AAAA Categories", categoriesRecipes.toString())
    //Log.d("AAAA SearchCateINgre", recipesByCategoryIngredients.toString())


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

                        Text(
                            text = user?.email ?: "Olla",
                            fontSize = 16.sp,
                            color = Slate900,
                            lineHeight = 19.sp,
                            modifier = Modifier
                                .clickable {
                                    homeViewModel.signOut()
                                    navController.navigate(route = "login")
                                }
                        )
                    }
                }
                //VerticalDivider(modifier = Modifier.padding(20.dp))
                //CategoryButton(navController)
                Image(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = stringResource(id = R.string.home_button),
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        item {
            SearchRecipeByIngredientsTextField(
                onAddIngredient = {
                    ingredient -> homeViewModel.onIngredientTextInput(ingredient)
                }
            )

        }
        item {
            CardFridge(
                selectedIngredients,
                onIngredientClick = { homeViewModel.addIngredient(it) }
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    //.background(Color.Magenta)
                    .fillMaxWidth()
                    .padding(20.dp,0.dp),
                    //.background(Color.Red),
                verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = A
            ){
                item {
                    val isSelectedAll = selectedCategory == null
                    Text(
                        text = "All",
                        fontSize = 14.sp,
                        color = if(isSelectedAll) Slate900  else Slate400,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(route = "categories")
                                //homeViewModel.addCategory(it[index])
                                //isSelected = !isSelected
                            }
                    )
                }
                categoriesRecipes?.let {
                    items(it.size){index ->
                        var isSelected = it[index] == selectedCategory
                        Text(
                            text = it[index].name,
                            fontSize = 14.sp,
                            color = if(isSelected) Slate900  else Slate400,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    homeViewModel.addCategory(it[index])
                                    isSelected = !isSelected
                                    //isSelectedAll = false
                                }
                        )
                    }
                }
            }
        }
        item {
            recipesToDisplay?.let { list ->
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                        //.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(list.size) { index ->
                        var isSaved by rememberSaveable { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            isSaved = homeViewModel.isRecipeSaved(list[index].id)
                        }
                        CardRecipeHomeScreen(
                            navController,
                            list[index],
                            onClick = {
                                coroutineScope.launch {
                                    if (homeViewModel.isRecipeSaved(list[index].id)) {
                                        homeViewModel.deleteSavedRecipe(list[index].id)
                                        isSaved = false
                                    } else {
                                        val recipe = homeViewModel.fetchMealById(list[index].id)
                                        homeViewModel.saveRecipeWithImage(context, recipe)
                                        Log.d("Кнопка збереження - не збережено", list[index].name)
                                        isSaved = true
                                    }
                                }
                            },isSaved)
                    }
                }
            }
        }

//        item {
//            if (recipesToDisplay.isNullOrEmpty()) {
//                recipesToDisplay?.let { list ->
//                    LazyRow(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(350.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(16.dp),
//                        contentPadding = PaddingValues(horizontal = 16.dp)
//                    ) {
////                        items(recipesToDisplay.orEmpty()) { recipe ->
////                            CardRecipeHomeScreen(navController, recipe)
////                        }
//                        items(list.size) { index ->
//                            CardRecipeHomeScreen(navController, list[index])
//                        }
//                    }
//                }
//            } else {
//            recipesRandom10?.let { list ->
//                LazyRow(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(350.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(horizontal = 16.dp)
//                ) {
//                    items(list.size) { index ->
//                        CardRecipeHomeScreen(navController, list[index])
//                    }
//                }
//            }
//            }
//        }

//        item {
//            LazyRow(
//                modifier = Modifier
//                    .fillParentMaxWidth()
//                    .height(350.dp),
//                //.background(Color.Cyan),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceAround
//
//            ) {
//                items(countRecipe){
//                    CardRecipeHomeScreen(navController)
//                }
//            }
//        }

    }
}

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
fun SearchRecipeByIngredientsTextField(
    onAddIngredient: (String) -> Unit
) {
    var inputTextIngredients by rememberSaveable { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(25.dp,10.dp)
        .height(70.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Gray100)){
        Row(modifier = Modifier
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            //SearchTextField(modifier = Modifier)

            TextField(
                value = inputTextIngredients,
                onValueChange = { inputTextIngredients = it },
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
            //VerticalDivider(modifier = Modifier.size(8.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Icon(modifier = Modifier
                .size(40.dp)
                .padding(5.dp)
                .clickable {
                    if (inputTextIngredients.isNotBlank()) {
                        onAddIngredient(inputTextIngredients.trim())
                        //inputTextIngredients = "" // очистити поле після додавання
                    }
                },
                painter = painterResource(R.drawable.search),
                contentDescription = stringResource(R.string.home_search_ingredient),
                tint = Gray400
            )
        }
    }
}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchTextField(modifier: Modifier) {
//    var text by remember { mutableStateOf("") }
//
//    TextField(
//        value = text,
//        onValueChange = { text = it },
//        placeholder = { Text("Введіть текст...", color = Gray400,fontSize = 14.sp,lineHeight = 14.sp ) },
//        colors = TextFieldDefaults.textFieldColors(
//            containerColor = Color.Transparent,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//            disabledIndicatorColor = Color.Transparent,
//            unfocusedTextColor = Gray400,
//            focusedTextColor = Slate900
//
//        ),
//        singleLine = true,
//        textStyle = TextStyle(
//            fontSize = 14.sp,
//            lineHeight = 14.sp
//        )
//    )
//}
@Composable
fun CardFridge(
    ingredients: List<String>,
    onIngredientClick: (String) -> Unit
){
    val ingredientCard = listOf(
        "Meat" to "🥩",
        "Chicken" to "🍗",
        "Fish" to "🐟",
        "Milk" to "🥛",
        "Flour" to "😶‍🌫️",
        "Sugar" to "🍪",
        "Eggs" to "🥚",
        "Broccoli" to "🥦",
        "Potato" to "🥔",
        //"Salmon",
        //"Beef",
        //"Pork",
        //"Avocado",
//        "Asparagus",
//        "Bacon",
//        "Basil",
//        "Basmati Rice",
//        "Black Pepper",
//        "Bread",
//        "Brown Sugar",
//        "Butter",
//        "Cacao",
//        "Carrots",
//        "Cheese",
//        "Chilli",
//        "Cream",
//        "Cucumber",
//        "Honey",
//        "Lemon",
//        "Mushrooms",
//        "Onion",
//        "Rice",
//        "Sausages",
//        "Spinach",
//        "Spaghetti",
//        "Tomato",
//        "Tuna",
//        "Duck",
    )
    Column(
        modifier = Modifier
            //.background(Color.Yellow)
            .fillMaxWidth()
            .height(200.dp)
            .padding(20.dp)
            //.background(Color.Cyan)
    ){
        Text(text = "What's in your fridge?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Slate900,

        )
        Spacer(modifier = Modifier.size(20.dp))
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2), // 2 стовпці
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)//.padding(20.dp)
        ) {
            items(ingredientCard.size) { index ->
                val nameIngredient = ingredientCard[index].first
                val emoji = ingredientCard[index].second
                val isSelected =  nameIngredient in ingredients
                IngredientCard(
                    ingredient = nameIngredient,
                    imageRes = emoji,
                    isSelected = isSelected,
                    onClick = onIngredientClick
                    //ingredients
                )
            }
        }
    }
}
@Composable
fun IngredientCard(
    ingredient: String,
    imageRes: String,
    isSelected: Boolean,
    onClick: (String) -> Unit
    //ingredients: MutableList<String>
) {
    val backgroundColor = if (isSelected) MyPrimeryOrang else Gray100
    Box(
        modifier = Modifier
            .width(110.dp)
            .height(38.dp)
            .padding(2.dp,3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .padding(4.dp)
            .clickable {
                onClick(ingredient)
//                ingredients.add(ingredient)
//                Log.d("AAAA LIST CARD", ingredients.toString())
            }
    ) {
        Row (
            modifier = Modifier.padding(6.dp).align(Alignment.Center),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ingredient + imageRes,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Gray400
            )
            //Spacer(modifier = Modifier.width(5.dp))
//            Text(
//                text = imageRes,
//                fontSize = 14.sp
//            )
//            Image(
//                painter = painterResource(id = imageRes),
//                contentDescription = ingredient,
//                modifier = Modifier.size(30.dp)
//            )

        }
    }
}

@Composable
fun CardRecipeHomeScreen(
    navController: NavHostController,
    recipe: RecipeShort,
    //viewModel: ViewModel
    onClick: () -> Unit,
    isSave: Boolean
){
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
        shape = RoundedCornerShape(12.dp),
        onClick = {
            //navController.navigate(route = "recipe")
            navController.navigate(AppRoute.recipeWithId(recipe.id))
        }
    ) {
        //var isSave by rememberSaveable { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            AsyncImage(
                model = recipe.thumbnail,
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                    //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            //Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = recipe.name + isSave.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Slate900,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp).height(40.dp)
                )
            //Spacer(modifier = Modifier.size(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp,10.dp),
                verticalAlignment = Alignment.CenterVertically,
                //horizontalArrangement = Arrangement.SpaceBetween
                horizontalArrangement = Arrangement.End
            ) {
//                Text(
//                    text = "Pasta, Italian",
//                    color = Slate400,
//                    fontSize = 12.sp
//                    )
                //Spacer(modifier = Modifier.weight(10.dp))
                Icon(
                    imageVector = if(isSave) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.icon_save),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            //isSave = !isSave
                            Log.d("isSaveIcon",isSave.toString())
                            onClick()
                        }

                )
            }
        }

    }
}