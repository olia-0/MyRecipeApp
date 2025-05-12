package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor (
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke(): List<Categories> = repository.getCategories()
}