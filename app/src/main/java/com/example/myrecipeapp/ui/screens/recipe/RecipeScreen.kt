package com.example.myrecipeapp.ui.screens.recipe

import android.util.Log
import android.widget.ImageView
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bumptech.glide.Glide
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.components.BackButton
import com.example.myrecipeapp.ui.components.SavedButton
import com.example.myrecipeapp.ui.components.SearchButton
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate400
import com.example.myrecipeapp.ui.theme.Slate500
import com.example.myrecipeapp.ui.theme.Slate900
import com.example.myrecipeapp.ui.theme.White
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.launch
import java.io.File


//@Preview(showSystemUi = true)
@Composable
fun RecipeScreen(
    navController: NavHostController,
    recipeId: String,
    viewModel: RecipeViewModel = hiltViewModel()
) {

    //val recipe by viewModel.getRecipe(recipeId).observeAsState()

    val meals by viewModel.selectedMeal.observeAsState()
    val instructionMeal by viewModel.mealInstructions.observeAsState()
    val videoId = viewModel.youtubeId

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var isSaved by rememberSaveable  { mutableStateOf(false) }
    var isVideoLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchMealById(recipeId.toString())
        meals?.let { viewModel.saveRecipeViewedWithImage(context,it) }
        meals?.let { Log.d("AAAAAA Name Recipe", it.nameRecipe) }
    }
    LaunchedEffect(meals) {
        meals?.let {
            isSaved = viewModel.isRecipeSaved(it.idRecipe)
            viewModel.saveRecipeViewedWithImage(context,it)
        }
    }



    LazyColumn {

        item {
            Box() {

                AsyncImage(
                    model = meals?.photoRecipe,
                    contentDescription = "Зображення обраного рецепту з Api",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentScale = ContentScale.Crop
                )
                Row(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .height(100.dp)
                        .padding(15.dp, 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BackButton(navController)
                    //SavedButton()
                    meals?.let { currentMeal ->
                        SavedButton(
                            isSaved = isSaved,
                            onClick = {
                                coroutineScope.launch {
                                    if (isSaved) {
                                        viewModel.deleteSavedRecipe(currentMeal.idRecipe)
                                        Log.d("Кнопка збереження - збережено", currentMeal.nameRecipe)
                                        isSaved = false
                                    } else {
                                        viewModel.saveRecipeWithImage(context, currentMeal)
                                        Log.d("Кнопка збереження - не збережено", currentMeal.nameRecipe)
                                        isSaved = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
        item {
            Column {
                meals?.nameRecipe?.let {
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Slate900,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(20.dp,10.dp),
                    thickness = 4.dp,
                    color = Gray100
                )
                Text(
                    text = "Інгредієнти",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(20.dp)
                )
                val ingredientList = meals?.ingredients?.split(",") ?: emptyList()
                val measureList = meals?.measures?.split(",") ?: emptyList()

                Column {
                    ingredientList.forEachIndexed { index, ingredient ->
                        val measure = measureList.getOrNull(index)
                        RowIngredient(ingredient, measure, index + 1)
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(20.dp,10.dp),
                    thickness = 4.dp,
                    color = Gray100
                )

                Text(
                    text = "Інструкція з приготування",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Slate900,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(20.dp)
                )
                instructionMeal?.forEachIndexed { index, step ->
                    Row {
                        Box(
                            modifier = Modifier
                                .padding(20.dp,5.dp)
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Gray100),
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "${index + 1}",
                                fontSize = 14.sp,
                                color = MyPrimeryOrang
                                //modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                        Text(
                            text = step,
                            fontSize = 14.sp,
                            color = Slate500,
                            modifier = Modifier.padding(0.dp,5.dp)
                        )
                    }
//                    Text(
//                        text = "${index + 1}: $step",
//                        style = MaterialTheme.typography.bodyMedium,
//                        modifier = Modifier.padding(top = 4.dp)
//                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(20.dp,10.dp),
                    thickness = 4.dp,
                    color = Gray100
                )
            }
        }
        item {
            if (videoId!= null) {
                //YouTubePlayerScreen(videoId = videoId)
                Button(
                    onClick = {
                        isVideoLoaded = !isVideoLoaded

                    },
                    colors = ButtonDefaults.buttonColors(MyPrimeryOrang)
                ) {
                    Text(text = if (!isVideoLoaded) "Відтворити відео" else "Відео запущено")
                }
                if(isVideoLoaded){
                    YouTubePlayerScreen(videoId = videoId)
                }
            }
        }
    }
}

@Composable
fun GlideImage(imagePath: String, modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            Glide.with(imageView.context)
                .load(File(imagePath))
                .into(imageView)
        },
        modifier = modifier
    )
}

@Composable
fun RowIngredient(
    ingredient: String?,
    measure: String?,
    index: Int
){
    if(ingredient != ""){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp,10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Gray100),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = index.toString(),
                fontSize = 14.sp,
                color = MyPrimeryOrang
                //modifier = Modifier.padding(top = 4.dp)
            )
        }
        ingredient?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Slate500,
                    modifier = Modifier.weight(1f)
                )
            }
        measure?.let {
                Text(
                    text = it,
                    fontSize = 14.sp,
                    color = Slate500,
                    modifier = Modifier.weight(1f)
                )
            }

//        Row(
//            modifier = Modifier.weight(1f),
//            horizontalArrangement = Arrangement.Absolute.Left
//        ) {
//            ingredient?.let {
//                Text(
//                    text = it,
//                    fontSize = 14.sp,
//                    color = Slate500,
//                )
//            }
//        }
//
//        Row(
//            modifier = Modifier.weight(1f),
//            horizontalArrangement = Arrangement.Absolute.Left
//        ) {
//            measure?.let {
//                Text(
//                    text = it,
//                    fontSize = 14.sp,
//                    color = Slate500
//                )
//            }
//        }
    }
    }
}

@Composable
fun YouTubePlayerScreen(videoId: String) {
    val context = LocalContext.current

    // AndroidView для інтеграції YouTubePlayer
    AndroidView(
        factory = { ctx ->
            // Створюємо YouTubePlayerView
            val youTubePlayerView = YouTubePlayerView(ctx)

            // Ініціалізація YouTubePlayer після готовності
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(player: YouTubePlayer) {
                    player.loadVideo(videoId, 0f) // Завантажуємо відео
                }
            })
            youTubePlayerView
        },
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(220.dp)
    )
}

@Composable
fun YouTubePlayerScreen1(videoId: String) {
    val context = LocalContext.current
    var youTubePlayer by remember { mutableStateOf<YouTubePlayer?>(null) }
    var isVideoLoaded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        AndroidView(
            factory = { ctx ->
                val youTubePlayerView = YouTubePlayerView(ctx)

                youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(player: YouTubePlayer) {
                        youTubePlayer = player // зберігаємо плеєр
                    }
                })

                youTubePlayerView
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Кнопка для запуску відео
        Button(
            onClick = {
                if (!isVideoLoaded) {
                    youTubePlayer?.loadVideo(videoId, 0f)
                    isVideoLoaded = true
                }
            },
            enabled = !isVideoLoaded
        ) {
            Text(text = if (!isVideoLoaded) "Відтворити відео" else "Відео запущено")
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