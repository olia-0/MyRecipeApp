package com.example.myrecipeapp.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.data.local.ImageStorage
import com.example.myrecipeapp.data.local.dao.CategoryDao
import com.example.myrecipeapp.data.local.entity.CategoryEntity
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.mapper.toCategories
import com.example.myrecipeapp.data.mapper.toCategory
import com.example.myrecipeapp.data.mapper.toRecipe
import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.Category
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: CategoryDao,

) : CategoriesRepository {


    override suspend fun getCategories(): List<Categories> {
        Log.d("API_DEBUG", apiService.getCategories().toString())
        return apiService.getCategories().categories.map { it.toCategories() }
    }

//    suspend fun saveCategory(context: Context, name: String, imageUrl: String) {
//        val imageResult = ImageStorage.downloadAndSaveImage(context, imageUrl, "category_${System.currentTimeMillis()}")
//            ?: throw Exception("Не вдалося завантажити зображення")
//
//        val (imagePath, _) = imageResult
//        val category = CategoryEntity(name = name, imagePath = imagePath)
//        dao.insert(category)
//    }
    override suspend fun getCategoriesDB(): List<Category> {
        return dao.getAllCategories().first().map { it.toCategory() }
    }
    override suspend fun saveCategory(category: CategoryEntity) {
        return dao.insert(category)
    }
    override suspend fun getCategoryByName(name: String): Category{
        return dao.getCategoryByName(name)?.toCategory() ?: error("Recipe Not Found")
    }

    suspend fun deleteCategory(id: Int) {
        val category = dao.getCategoryById(id)
        category?.let {
            File(it.imagePath).delete()
            dao.deleteById(id)
        }
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