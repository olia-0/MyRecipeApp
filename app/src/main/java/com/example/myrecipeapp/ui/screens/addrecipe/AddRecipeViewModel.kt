package com.example.myrecipeapp.ui.screens.addrecipe

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.remote.dto.RecipeDto
import com.example.myrecipeapp.domain.repository.RecipeRepository
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val repository: RecipeRepository // або будь-яка інша залежність
): ViewModel(){

    private val _state = MutableStateFlow<AddRecipeState>(AddRecipeState())
    val state: StateFlow<AddRecipeState> = _state.asStateFlow()

    fun submitRecipe(recipeDto: RecipeDto) {
        viewModelScope.launch {
            try {
                repository.addRecipe(recipeDto)
                _state.value = _state.value.copy(success = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    private val _imageUrl = mutableStateOf("")
    val imageUrl: State<String> = _imageUrl

    private val _uploading = mutableStateOf(false)
    val uploading: State<Boolean> = _uploading

    fun uploadImageToFirebase(uri: Uri, context: Context) {
        _uploading.value = true
        val storageRef = Firebase.storage.reference.child("recipe_images")
        Log.d("AAA  storageRef",storageRef.toString())
        val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")
        Log.d("AAA  imageRef",imageRef.toString())
        imageRef.putBytes(
            bitMapUrl(context,uri)
        )
            .addOnSuccessListener {
                Log.d("FirebaseUpload1", "Upload successful")
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    Log.d("FirebaseUpload2", "Download URL: $downloadUrl")
                    _imageUrl.value = downloadUrl.toString()
                    Log.d("imageUrl.value", "Download URL: $downloadUrl")
                    _uploading.value = false
                    //Log.d("FirebaseUpload2", "Download URL: $downloadUrl")
                }
            }
            .addOnFailureListener {
                Log.e("FirebaseUpload3", "Upload failed", it)
                Toast.makeText(context, "Помилка при завантаженні: ${it.message}", Toast.LENGTH_SHORT).show()
                _uploading.value = false
            }

//        val imageRef = storageRef.child("${UUID.randomUUID()}.jpg")
//        Log.d("AAA  imageRef",imageRef.toString())
//
//        imageRef.putFile(uri)
//            .addOnSuccessListener {
//                Log.d("FirebaseUpload1", "Upload successful")
//                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
//                    Log.d("FirebaseUpload2", "Download URL: $downloadUrl")
//                    _imageUrl.value = downloadUrl.toString()
//                    Log.d("imageUrl.value", "Download URL: $downloadUrl")
//                    _uploading.value = false
//                    //Log.d("FirebaseUpload2", "Download URL: $downloadUrl")
//                }
//            }
//            .addOnFailureListener {
//                Log.e("FirebaseUpload3", "Upload failed", it)
//                Toast.makeText(context, "Помилка при завантаженні: ${it.message}", Toast.LENGTH_SHORT).show()
//                _uploading.value = false
//            }
    }
    fun bitMapUrl(context: Context, uri: Uri):ByteArray{
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,baos)
        return baos.toByteArray()
    }
}

data class AddRecipeState(
    val success: Boolean = false,
    val error: String? = null
)
