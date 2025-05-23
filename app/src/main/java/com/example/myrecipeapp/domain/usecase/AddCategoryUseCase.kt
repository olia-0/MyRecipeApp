package com.example.myrecipeapp.domain.usecase

import android.content.Context
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.entity.CategoryEntity
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class AddCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(context: Context, name: String, imageUrl: String) {
        val sanitizedFileName = "category_${System.currentTimeMillis()}"
        val imageResult = ImageStorage.downloadAndSaveImage(context, imageUrl, sanitizedFileName)
            ?: throw Exception("Не вдалося завантажити зображення")

        val (localPath, _) = imageResult
        val category = CategoryEntity(name = name, imagePath = localPath)
        repository.saveCategory(category)
    }
}
