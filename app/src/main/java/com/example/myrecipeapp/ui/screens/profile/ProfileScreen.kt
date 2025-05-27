package com.example.myrecipeapp.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.screens.addrecipe.IngredientItem
import com.example.myrecipeapp.ui.screens.addrecipe.IngredientList
import com.example.myrecipeapp.ui.screens.addrecipe.RecipeNameTextField
import com.example.myrecipeapp.ui.screens.saved.CardRecipeCategorySaved
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang
import com.example.myrecipeapp.ui.theme.Slate900


@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val profile by viewModel.userProfile.collectAsState()
    val recipes by viewModel.savedRecipes.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    var edit by rememberSaveable { mutableStateOf(false) }

    //var allergiesInput by remember { mutableStateOf(profile.allergies.joinToString(", ")) }
    //var nameInput by remember { mutableStateOf(profile.username) }
    var nameInput by rememberSaveable { mutableStateOf(profile.username) }
    var allergies by rememberSaveable { mutableStateOf(listOf<String>("")) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillParentMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Профіль",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate900,
                    modifier = Modifier.weight(4f)
                )
                IconButton(
                    onClick = {
                        edit = !edit
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.Edit,
                        contentDescription = "Edit_profile",
                        tint = MyPrimeryOrang)
                }
                IconButton(
                    onClick = {
                        edit = !edit
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Exit_profile",
                        tint = MyPrimeryOrang)
                }
            }
        }
        item {
            //Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(R.drawable.fox),
                contentDescription = "Photo_profile",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = profile.username,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Slate900
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
//        item {
//            Column(
//                modifier = Modifier.fillMaxWidth().padding(20.dp)
//            ) {
//                recipes.chunked(2).forEach { rowItems ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        //.padding(4.dp),
//                        horizontalArrangement = Arrangement.spacedBy(9.dp)
//                    ) {
//                        rowItems.forEach { recipe ->
//                            Box(modifier = Modifier.weight(1f)) {
//                                CardRecipeCategorySaved(
//                                    navController,
//                                    recipe,
//                                    coroutineScope,
//                                    onClick = {
//                                        //viewModel.deleteSavedRecipe(recipe.idRecipe)
//                                    },
//                                    true
//                                )
//                            }
//                        }
//                        // Якщо непарна кількість — додай порожній блок для вирівнювання
//                        if (rowItems.size < 2) {
//                            Spacer(modifier = Modifier.weight(1f))
//                        }
//                    }
//                }
//            }
//        }
        item {
            if(edit){
                Text(
                    text = "Логін",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate900,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                RecipeNameTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray100)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Алергії",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Slate900,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    //Text("Інгредієнти", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    AllergyList(
                        allergies = allergies,
                        onAllergyChange = { index, value ->
                            allergies = allergies.toMutableList().also {
                                it[index] = value
                            }
                        },
                        onAddClick = {
                            allergies = allergies + ""
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = {
                    viewModel.updateUser(nameInput,allergies)
                    //Log.d("Name",profile.username)
                    //Log.d("алергії",allergies.toString())
                    //val updatedAllergies = allergiesInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
//                    if(nameInput.isNotEmpty()){
//                        Log.d("Name1",profile.username)
//                        //viewModel.updateName(nameInput)
//
//                        //nameInput = ""
//                        Log.d("Name2",profile.username)
//                    }
//                    if(allergies.isNotEmpty()){
//                        viewModel.updateAllergies(allergies)
//                    }


                },
                    colors = ButtonDefaults.buttonColors(MyPrimeryOrang),
                    ) {
                    Text("Оновити")
                }
            }else {
                Column(
                    modifier = Modifier.fillMaxWidth(),//.padding(20.dp)
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Мої рецепти",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray400
                    )
                    Spacer(modifier = Modifier.height(20.dp))

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
                                            //viewModel.deleteSavedRecipe(recipe.idRecipe)
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
        }
    }


//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Привіт, ${profile.username}", fontSize = 24.sp)
//
//        OutlinedTextField(
//            value = nameInput,
//            onValueChange = { nameInput = it },
//            label = { Text("Name") }
//        )
//
//        Button(onClick = {
//            //val updatedAllergies = allergiesInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
//            viewModel.updateName(nameInput)
//        }) {
//            Text("Оновити алергії")
//        }
//        OutlinedTextField(
//            value = allergiesInput,
//            onValueChange = { allergiesInput = it },
//            label = { Text("Алергії (через кому)") }
//        )
//
//        Button(onClick = {
//            val updatedAllergies = allergiesInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
//            viewModel.updateAllergies(updatedAllergies)
//        }) {
//            Text("Оновити алергії")
//        }
//
//    }
}

@Composable
fun AllergyList(
    allergies: List<String>,
    onAllergyChange: (index: Int, value: String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        //horizontalArrangement = Arrangement.SpaceBetween
        ) {
            allergies.forEachIndexed { index, allergy ->
                AllergyItem(
                    allergy = allergy,
                    onValueChange = { newValue -> onAllergyChange(index, newValue) },
                    onAddClick
                )
            }
            //Spacer(modifier = Modifier.height(10.dp))

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllergyItem(
    allergy: String,
    onValueChange: (String) -> Unit,
    onAddClick: () -> Unit

) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = allergy,
            onValueChange = onValueChange,
            placeholder = { Text("Введіть алергію") },
            modifier = Modifier
                .width(300.dp)
                .height(70.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Gray100),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedTextColor = Gray400,
                focusedTextColor = Slate900
            ),
            textStyle = TextStyle(fontSize = 14.sp)
        )
        IconButton(
            onClick = onAddClick,
            colors = IconButtonDefaults.iconButtonColors(MyPrimeryOrang),
            modifier = Modifier
                .height(40.dp)
                .width(40.dp),) {
            Icon(
                painter = painterResource(R.drawable.add_icon),
                contentDescription = "Додати інгредієнт",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
