package com.example.myrecipeapp.data.repositoryImpl

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