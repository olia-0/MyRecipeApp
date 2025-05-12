package com.example.myrecipeapp.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.myrecipeapp.ui.theme.Gray400
import com.example.myrecipeapp.ui.theme.Slate900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    //text: MutableSt
    modifier: Modifier,
) {
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