package com.example.myrecipeapp.domain.usecase

import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryByNameUseCase @Inject constructor(
    private val repository: CategoriesRepository
){
    suspend operator fun invoke(name: String): Category {
        return repository.getCategoryByName(name)
    }
}