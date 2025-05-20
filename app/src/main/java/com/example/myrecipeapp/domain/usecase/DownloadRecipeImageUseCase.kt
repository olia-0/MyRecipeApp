package com.example.myrecipeapp.domain.usecase

import android.content.Context
import com.example.myrecipeapp.domain.repository.RecipeRepository
import javax.inject.Inject

class DownloadRecipeImageUseCase @Inject constructor(private val repo: RecipeRepository) {
    suspend operator fun invoke(context: Context, url: String, id: String): String? {
        return repo.downloadRecipeImage(context, url, id)
    }
}
