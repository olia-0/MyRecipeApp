package com.example.myrecipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myrecipeapp.R
import com.example.myrecipeapp.ui.theme.Gray100
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.Slate900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchRecipeTextField(

){
    //var inputTextIngredients by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(25.dp,10.dp)
        .height(70.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Gray100)){
        Row(modifier = Modifier
            .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            SearchTextField(modifier = Modifier)
//            TextField(
//                value = inputTextIngredients,
//                onValueChange = { inputTextIngredients = it },
//                placeholder = { Text("Введіть текст...", color = Gray400,fontSize = 14.sp,lineHeight = 14.sp ) },
//                colors = TextFieldDefaults.textFieldColors(
//                    containerColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent,
//                    unfocusedTextColor = Gray400,
//                    focusedTextColor = Slate900
//
//                ),
//                singleLine = true,
//                textStyle = TextStyle(
//                    fontSize = 14.sp,
//                    lineHeight = 14.sp
//                )
//            )

            //VerticalDivider(modifier = Modifier.size(8.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Icon(modifier = Modifier
                .size(40.dp)
                .padding(5.dp),
                painter = painterResource(R.drawable.search),
                contentDescription = stringResource(R.string.home_search_ingredient),
                tint = Gray400
            )
        }
    }
}