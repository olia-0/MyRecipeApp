package com.example.myrecipeapp.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val profile by viewModel.userProfile.collectAsState()

    var allergiesInput by remember { mutableStateOf(profile.allergies.joinToString(", ")) }
    var nameInput by remember { mutableStateOf(profile.username) }


    Column(modifier = Modifier.padding(16.dp)) {
        Text("Привіт, ${profile.username}", fontSize = 24.sp)

        OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Name") }
        )

        Button(onClick = {
            //val updatedAllergies = allergiesInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            viewModel.updateName(nameInput)
        }) {
            Text("Оновити алергії")
        }
        OutlinedTextField(
            value = allergiesInput,
            onValueChange = { allergiesInput = it },
            label = { Text("Алергії (через кому)") }
        )

        Button(onClick = {
            val updatedAllergies = allergiesInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
            viewModel.updateAllergies(updatedAllergies)
        }) {
            Text("Оновити алергії")
        }
    }
}
