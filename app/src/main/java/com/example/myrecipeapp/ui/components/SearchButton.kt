package com.example.myrecipeapp.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myrecipeapp.R

@Composable
fun SearchButton(
    navController: NavHostController
){
    IconButton(
        modifier = Modifier
            .size(30.dp),
        onClick = {
            navController.navigate("search")
        }
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.icon_search),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}