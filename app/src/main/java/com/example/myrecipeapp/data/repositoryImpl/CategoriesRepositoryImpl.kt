package com.example.myrecipeapp.data.repositoryImpl

import android.util.Log
import com.example.myrecipeapp.data.mapper.toCategories
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
): CategoriesRepository{

    override suspend fun getCategories(): List<Categories> {
        Log.d("API_DEBUG", apiService.getCategories().toString())
        return apiService.getCategories().categories.map { it.toCategories() }
    }
}
//class CategoriesRepositoryImpl(
//    private val apiService: ApiService,
//    private val categoriesDao: CategoriesDao
//) {
//    // Отримати категорії з API
//    suspend fun getCategoriesFromApi(): List<Category> {
//        val response = apiService.getCategories()  // Ти викликаєш API
//        return response.categories.map { CategoryMapper.fromDto(it) }
//    }
//
//    // Отримати категорії з локальної БД
//    suspend fun getCategoriesFromDb(): List<Category> {
//        val entities = categoriesDao.getAllCategories()
//        return entities.map { CategoryMapper.fromEntity(it) }
//    }
//
//    // Зберегти категорії в БД
//    suspend fun saveCategoriesToDb(categories: List<Category>) {
//        categoriesDao.insertAll(categories.map { CategoryMapper.toEntity(it) })
//    }
//}