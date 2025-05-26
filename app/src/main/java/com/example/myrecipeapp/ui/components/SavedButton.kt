package com.example.myrecipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.theme.MyPrimeryOrang

@Composable
fun SavedButton(
    //navController: NavHostController
    isSaved: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MyPrimeryOrang),
        contentAlignment = Alignment.Center
    ){
        IconButton(
            modifier = Modifier
                .size(30.dp),
            onClick = {
                onClick()
                //navController.popBackStack()
            }
        ) {
            Icon(
                imageVector = if(isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.icon_button_back),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}