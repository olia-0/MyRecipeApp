package com.example.myrecipeapp.ui.screens.addrecipe

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.myrecipeapp.R
import com.example.myrecipeapp.data.remote.dto.RecipeDto
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate900
import java.util.UUID


@Composable
fun AddRecipeScreen(
    viewModel : AddRecipeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    var name by rememberSaveable { mutableStateOf("") }
    //var ingredients by rememberSaveable { mutableStateOf("") }
    var photoRecipe by rememberSaveable { mutableStateOf("") }
    var categoryRecipe by rememberSaveable { mutableStateOf("") }
    var areaRecipe by rememberSaveable { mutableStateOf("") }
    var instructionsRecipe by rememberSaveable { mutableStateOf("") }
    //var tagsRecipe by rememberSaveable { mutableStateOf("") }
    var youtubeRecipe by rememberSaveable { mutableStateOf("") }
    //var measures by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var nutritionCalories by rememberSaveable { mutableStateOf("") }
    var nutritionProteins by rememberSaveable { mutableStateOf("") }
    var nutritionFats by rememberSaveable { mutableStateOf("") }
    var nutritionCarbs by rememberSaveable { mutableStateOf("") }
    var cookingTime by rememberSaveable { mutableStateOf("") }


    var ingredientList by rememberSaveable { mutableStateOf(listOf<Pair<String, String>>("" to "")) }
    val ingredients = ingredientList.joinToString(",") { it.first.trim() }
    val measures = ingredientList.joinToString(",") { it.second.trim() }

    val context = LocalContext.current
    val recipeOptions = listOf("Другі страви", "Десерти", "Супи", "Напої")


    val imageUrl by viewModel.imageUrl
    val uploading by viewModel.uploading

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            viewModel.uploadImageToFirebase(it, context)
        }
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp, 40.dp)
    ) {
        item {
            Text(
                text = "Додати рецепт",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900
            )
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Назва рецепта",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecipeNameTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)
            )
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Опис",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecipeDescriptionTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)
            )
        }
//        item {
//            Spacer(modifier = Modifier.height(20.dp))
//            Text(
//                text = "Фото",
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium,
//                color = Slate900,
//                modifier = Modifier
//            )
//            Spacer(modifier = Modifier.height(20.dp))
//            RecipeDescriptionTextField(
//                value = photoRecipe,
//                onValueChange = { photoRecipe = it },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(min = 100.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .background(Gray100)
//            )
//        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Час приготування",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )
                RecipeNameTextField(
                    value = cookingTime,
                    onValueChange = { cookingTime = it },
                    modifier = Modifier
                        .weight(1f)
                        //.padding(25.dp,10.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Категорія",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )
                RecipeNameDropdownMenu(
                    modifier = Modifier
                        .weight(1f)
                        //.padding(25.dp,10.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100),
                    options = recipeOptions,
                    selectedOption = categoryRecipe,
                    onOptionSelected = { selected ->
                        categoryRecipe = selected
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier
                    .fillParentMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Кухня",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate900,
                    modifier = Modifier.weight(1f)
                )
                RecipeNameTextField(
                    value = areaRecipe,
                    onValueChange = { areaRecipe = it },
                    modifier = Modifier
                        .weight(1f)
                        //.padding(25.dp,10.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100)
                )
            }
        }
//        item {
//            Spacer(modifier = Modifier.height(30.dp))
//            Column() {
//                Text("Інгредієнти", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
//                IngredientList()
//            }
//        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Column {
                Text("Інгредієнти", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                IngredientList(
                    ingredients = ingredientList,
                    onIngredientChange = { index, value ->
                        ingredientList = ingredientList.toMutableList().also {
                            it[index] = value to it[index].second
                        }
                    },
                    onMeasureChange = { index, value ->
                        ingredientList = ingredientList.toMutableList().also {
                            it[index] = it[index].first to value
                        }
                    },
                    onAddClick = {
                        ingredientList = ingredientList + ("" to "")
                    }
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Опис",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecipeDescriptionTextField(
                value = instructionsRecipe,
                onValueChange = { instructionsRecipe = it },
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(25.dp,10.dp)
                    .heightIn(min = 150.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)

            )
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Посилання на YouTube",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(20.dp))
            RecipeNameTextField(
                value = youtubeRecipe,
                onValueChange = { youtubeRecipe = it },
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(25.dp,10.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)

            )
        }
        item {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        launcher.launch(
                            PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }) {
                    Text("Обрати зображення")
                }

                if (uploading) {
                    CircularProgressIndicator()
                }

                selectedImageUri?.let {
                    Image(
                        painter = rememberAsyncImagePainter(it),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }

                if (imageUrl.isNotBlank()) {
                    Text(text = "URL зображення:", fontWeight = FontWeight.Bold)
                    Text(text = imageUrl, fontSize = 12.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(20.dp))

            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    val recipe = RecipeDto(
                        idRecipe = UUID.randomUUID().toString(),
                        nameRecipe = name ?: "",
                        photoRecipe = imageUrl ?: "",
                        categoryRecipe = categoryRecipe,
                        areaRecipe = areaRecipe ?: "",
                        instructionsRecipe = instructionsRecipe ?: "",
                        tagsRecipe = "",
                        youtubeRecipe = youtubeRecipe ?: "",
                        ingredients = ingredients ?: "",
                        measures = measures ?: "",
                        description = description ?: "",
                        nutritionCalories = 0,
                        nutritionProteins = 0f,
                        nutritionFats = 0f,
                        nutritionCarbs = 0f,
                        cookingTime = cookingTime ?: ""
                    )
                    viewModel.submitRecipe(recipe)
                },
                colors = ButtonDefaults.buttonColors(MyPrimeryOrang),
            ) {
                Text("Додати рецепт")
            }
        }
    }

}



//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            selectedImageUri = it
//            viewModel.uploadImageToFirebase(it, context)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text("Обрати зображення")
//        }
//
//        if (uploading) {
//            CircularProgressIndicator()
//        }
//
//        selectedImageUri?.let {
//            Image(
//                painter = rememberAsyncImagePainter(it),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(200.dp)
//                    .fillMaxWidth(),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        if (imageUrl.isNotBlank()) {
//            Text(text = "URL зображення:", fontWeight = FontWeight.Bold)
//            Text(text = imageUrl, fontSize = 12.sp, color = Color.Gray)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//    }

    //Button(
        //onClick = {}
//        onClick = {
//        val recipe = RecipeDto(
//            idRecipe = UUID.randomUUID().toString(),
//            nameRecipe = name,
//            photoRecipe = "",//imageUrl,
//            categoryRecipe = "",
//            areaRecipe = "",
//            instructionsRecipe = "",
//            tagsRecipe = "",
//            youtubeRecipe = "",
//            ingredients = ingredients,
//            measures = measures,
//            description = "",
//            nutritionCalories = 0,
//            nutritionProteins = 0f,
//            nutritionFats = 0f,
//            nutritionCarbs = 0f,
//            cookingTime = ""
//        )
//        viewModel.submitRecipe(recipe)
//    }
    //) {
        //Text("Додати рецепт")
    //}




//    state.error?.let {
//        Text("Помилка: $it", color = Color.Red)
//    }


//}


@Composable
fun AddRecipeScreen1(
    viewModel : AddRecipeViewModel = hiltViewModel()
){
    val state by viewModel.state.collectAsState()

    var name by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var measures by remember { mutableStateOf("") }
    // і т.д. для кожного поля

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Назва рецепта") })
        OutlinedTextField(value = ingredients, onValueChange = { ingredients = it }, label = { Text("Інгредієнти") })
        OutlinedTextField(value = measures, onValueChange = { measures = it }, label = { Text("Кількості") })
        // інші поля…

        Button(onClick = {
            val recipe = RecipeDto(
                idRecipe = UUID.randomUUID().toString(),
                nameRecipe = name,
                photoRecipe = "",
                categoryRecipe = "",
                areaRecipe = "",
                instructionsRecipe = "",
                tagsRecipe = "",
                youtubeRecipe = "",
                ingredients = ingredients,
                measures = measures,
                description = "",
                nutritionCalories = 0,
                nutritionProteins = 0f,
                nutritionFats = 0f,
                nutritionCarbs = 0f,
                cookingTime = ""
            )
            viewModel.submitRecipe(recipe)
        }) {
            Text("Додати рецепт")
        }

//        if (state.success) {
//            LaunchedEffect(Unit) {
//                val navController
//                navController.popBackStack()
//            }
//        }

        state.error?.let {
            Text("Помилка: $it", color = Color.Red)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeNameTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier,
    ){
        var inputName by rememberSaveable { mutableStateOf("") }

        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                //onValueChange = {
                    //inputName = it
                    //if (inputName.isNotBlank()) {
                        //inputName = ""
                    //}
                    //},
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
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDescriptionTextField(
    value: String = "",
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier,
    ){
        var inputName by rememberSaveable { mutableStateOf("") }

        Row(modifier = Modifier
            .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Введіть текст...", color = Gray400,fontSize = 14.sp,lineHeight = 14.sp ) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedTextColor = Gray400,
                    focusedTextColor = Slate900
                ),
                singleLine = false,
                //maxLines = 5,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeNameDropdownMenu(
    modifier: Modifier,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    //var selectedOption by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = selectedOption,
                onValueChange = { },
                readOnly = true, // 👈 важливо: поле тільки для читання
                placeholder = {
                    Text(
                        "Оберіть назву рецепта...",
                        color = Gray400,
                        fontSize = 14.sp,
                        lineHeight = 14.sp
                    )
                },
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
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true } // 👈 щоб відкривалося при кліку
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.95f) // 👈 щоб не виходило за межі
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            //selectedOption = option
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun IngredientList(
    ingredients: List<Pair<String, String>>, // (ingredient, measure)
    onIngredientChange: (index: Int, ingredient: String) -> Unit,
    onMeasureChange: (index: Int, measure: String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ingredients.forEachIndexed { index, pair ->
            IngredientItem(
                ingredient = pair.first,
                measure = pair.second,
                onIngredientChange = { newValue -> onIngredientChange(index, newValue) },
                onMeasureChange = { newValue -> onMeasureChange(index, newValue) },
                onAddClick = onAddClick
            )
        }
    }
}
//) {
//    var items by remember { mutableStateOf(listOf(0)) } // список індексів або будь-яких id
//
//    Column(modifier = Modifier.fillMaxWidth()) {
//        items.forEachIndexed { index, _ ->
//            IngredientItem(
//                onAddClick = {
//                    items = items + (items.size) // додаємо новий item з унікальним індексом
//                }
//            )
//        }
//    }
//}


@Composable
fun IngredientItem(
    ingredient: String,
    measure: String,
    onIngredientChange: (String) -> Unit,
    onMeasureChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(20.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Інгредієнт",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900
            )
            RecipeNameTextField(
                value = ingredient,
                onValueChange = onIngredientChange,
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Кількість",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900
            )
            RecipeNameTextField(
                value = measure,
                onValueChange = onMeasureChange,
                modifier = Modifier
                    .width(140.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Gray100)
            )
        }

        IconButton(
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),
                //.weight(1f),
            onClick = onAddClick
        ) {
            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = "Додати інгредієнт",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}