package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class ClearAllCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke() {
        repository.clearAllCategories()
    }
}