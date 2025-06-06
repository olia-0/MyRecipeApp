package com.example.myrecipeapp.ui.screens.saved

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.myrecipeapp.R
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.navigation.AppRoute
import com.example.myrecipeapp.ui.components.SearchRecipeByNameTextField
import com.example.myrecipeapp.ui.components.SearchRecipeTextField
import com.example.myrecipeapp.ui.screens.home.CardRecipeHomeScreen
import com.example.myrecipeapp.ui.screens.home.HomeViewModel
import com.example.myrecipeapp.ui.screens.home.SearchRecipeByIngredientsTextField
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray300
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.Gray50
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate400
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SavedScreen(
    navController: NavHostController,
    viewModel: SavedViewModel = hiltViewModel()
) {
    val recipes by viewModel.savedRecipes.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val memoryUsage by viewModel.memoryUsage.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var edit by rememberSaveable { mutableStateOf(false) }
    LazyColumn (
        modifier = Modifier
            .fillMaxSize(),
            //.padding(15.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Збережені рецепти",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.weight(3f)
                )
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Info,
                        contentDescription = "Edit_profile",
                        tint = MyPrimeryOrang
                    )
                }
                if (showDialog && memoryUsage != null) {
                    val (used, max) = memoryUsage!!
                    MemoryUsageDialog(
                        used = used,
                        max = max,
                        onDismiss = { showDialog = false },
                        onClear = { }//viewModel.clearSavedRecipes() }
                    )
                }
            }
        }
//        item {
//            Text(
//                text = "Збережені рецепти",
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                color = Slate900,
//                modifier = Modifier.padding(20.dp)
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//        }
//        item {
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Button(
//                onClick = { showDialog = true },
//                modifier = Modifier.padding(8.dp)
//            ) {
//                Text("Інформація про пам’ять")
//            }
//
//            if (showDialog && memoryUsage != null) {
//                val (used, max) = memoryUsage!!
//                MemoryUsageDialog(
//                    used = used,
//                    max = max,
//                    onDismiss = { showDialog = false },
//                    onClear = { }//viewModel.clearSavedRecipes() }
//                )
//            }
//        }
        //MemoryUsageInfo(viewModel)
        //item {
            //Spacer(modifier = Modifier.size(20.dp))
            //SearchRecipeByNameTextField()
        //}
        item {
            //TabSaved(navController,recipes, coroutineScope,viewModel)
            SavedTable(navController,recipes, coroutineScope,viewModel)
        }

    }
    //Text(text = "SavedScreen", fontSize = 100.sp)
    //TabSaved(navController,recipes)


}
//
////@Preview(showSystemUi = true)
@Composable
fun TabSaved(
    navController: NavHostController,
    recipes: List<Recipe>,
    coroutineScope: CoroutineScope,
    viewModel: SavedViewModel
){
    val tabs = listOf("Saved", "My recipe")
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index }
                )
            }
        }

        // Відображення таблиці залежно від вкладки
        when (selectedTabIndex) {
            0 -> SavedTable(
                navController,
                recipes,
                coroutineScope,
                viewModel)
            1 -> UserRecipeTable(navController)
        }
    }
}

//@Composable
//fun SavedScreen(
//    navController: NavHostController,
//    viewModel: SavedViewModel = hiltViewModel()
//) {
//    val recipes by viewModel.savedRecipes.collectAsState()
//    val coroutineScope = rememberCoroutineScope()
//    var selectedTabIndex by remember { mutableStateOf(0) }
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(15.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        item {
//            SearchRecipeTextField()
//        }
//
//        item {
//            TabRow(selectedTabIndex = selectedTabIndex) {
//                listOf("Saved", "My recipe").forEachIndexed { index, title ->
//                    Tab(
//                        text = { Text(title) },
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index }
//                    )
//                }
//            }
//        }
//
//        when (selectedTabIndex) {
//            0 -> {
//                items(recipes) { recipe ->
//                    CardRecipeCategorySaved(navController, recipe, coroutineScope, viewModel)
//                }
//            }
//            1 -> {
//                item {
//                    UserRecipeTable(navController)
//                }
//            }
//        }
//    }
//}

@Composable
fun SavedTable1(
    navController: NavHostController,
    recipes: List<Recipe>,
    coroutineScope: CoroutineScope,
    viewModel: SavedViewModel
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(10.dp,),
        modifier = Modifier.fillMaxSize()//.padding(8.dp)
    ) {
        items(recipes.size){index ->
            //CardRecipeCategorySaved(navController,recipes[index],coroutineScope,viewModel)
        }
    }
}
@Composable
fun SavedTable(
    navController: NavHostController,
    recipes: List<Recipe>,
    coroutineScope: CoroutineScope,
    //onClick: () -> Unit
    viewModel: SavedViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        recipes.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    //.padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                rowItems.forEach { recipe ->
                    Box(modifier = Modifier.weight(1f)) {
                        CardRecipeCategorySaved(
                            navController,
                            recipe,
                            coroutineScope,
                            onClick = {
                                viewModel.deleteSavedRecipe(recipe.idRecipe)
                            },
                            true
                        )
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


@Composable
fun UserRecipeTable(
    navController: NavHostController
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(5.dp,),
        modifier = Modifier.fillMaxSize()//.padding(8.dp)
    ) {
        items(14){
            //CardRecipeCategorySaved(navController,null)
        }
    }
}


@Composable
fun CardRecipeCategorySaved(
    navController: NavHostController,
    recipe: Recipe,
    coroutineScope: CoroutineScope,
    onClick: () -> Unit,
    isSave: Boolean
    //viewModel: SavedViewModel
){
    Card(modifier = Modifier
        .width(160.dp)
        //.fillMaxWidth()
        .height(240.dp)
        //.padding(10.dp)
        .shadow(
            elevation = 10.dp, // Висота тіні
            shape = RoundedCornerShape(8.dp), // Форма тіні
            ambientColor = Gray400, // Колір основної тіні
            spotColor = Gray300 // Колір блиску (spot) тіні
        ),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = MyPrimeryOrang,
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            //navController.navigate(route = "recipe")
            navController.navigate(AppRoute.recipeWithId(recipe.idRecipe))
        }
    ) {
        //var isSave by rememberSaveable { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween

        ) {
            AsyncImage(
                model = recipe?.photoRecipe ?: "https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg",
                contentDescription = "Зображення з Інтернету",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(145.dp),
                //.clip(CircleShape), // Обрізає у круглу форму
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = recipe?.nameRecipe ?: "Ooops",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900,
                overflow = TextOverflow.Ellipsis,
                //lineHeight = 16.sp,
                modifier = Modifier.padding(8.dp,0.dp)
            )
            //Spacer(modifier = Modifier.size(5.dp))
            Row(
                modifier = Modifier.padding(10.dp,0.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = recipe.areaRecipe ?: "",
                    color = Slate400,
                    fontSize = 10.sp
                )
                //Spacer(modifier = Modifier.weight(10.dp))

                Icon(
                    imageVector = if(isSave) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(R.string.icon_save),
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            //isSave = !isSave
                                coroutineScope.launch {
                                    //if (isSave) {
                                        onClick()
                                        //recipe?.idRecipe?.let { viewModel.deleteSavedRecipe(it) }
                                        //Log.d("Кнопка збереження - збережено", currentMeal.nameRecipe)
                                        //isSave = false
                                    //}
                                    onClick()
                                }
                            }


                )
            }
        }

    }
}
@Composable
fun MemoryUsageInfo1(viewModel: SavedViewModel) {
    val memoryUsage by viewModel.memoryUsage.collectAsState()

    memoryUsage?.let { (used, max) ->
        val usedStr = formatBytes(used)
        val maxStr = formatBytes(max)

        Text(
            text = "Зайнято пам’яті: $usedStr із $maxStr ",
            //style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun MemoryUsageDialog(
    used: Long,
    max: Long,
    onDismiss: () -> Unit,
    onClear: () -> Unit
) {
    val usedMB = used.toFloat() / (1024 * 1024)
    val maxMB = max.toFloat() / (1024 * 1024)
    val progress = (used.toFloat() / max.toFloat()).coerceIn(0f, 1f)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Використання пам’яті", fontSize = 14.sp) },
        containerColor = Gray100,
        text = {
            Column {
                Text("Зайнято: ${String.format("%.2f", usedMB)} MB із ${String.format("%.2f", maxMB)} MB")
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = progress,
                    color = Slate900,
                    trackColor = Slate400,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onClear()
                onDismiss()
            },
                //colors = ButtonDefaults.textButtonColors(MyPrimeryOrang)
            ) {
                Text("Очистити рецепти", color = MyPrimeryOrang)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрити", color = MyPrimeryOrang)

            }
        }
    )
}

fun formatBytes(bytes: Long): String {
    val kb = 1024L
    val mb = kb * 1024
    val gb = mb * 1024

    return when {
        bytes >= gb -> String.format("%.2f GB", bytes.toDouble() / gb)
        bytes >= mb -> String.format("%.2f MB", bytes.toDouble() / mb)
        bytes >= kb -> String.format("%.2f KB", bytes.toDouble() / kb)
        else -> "$bytes B"
    }
}