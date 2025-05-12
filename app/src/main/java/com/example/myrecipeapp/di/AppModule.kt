package com.example.myrecipeapp.di

import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.data.repositoryImpl.CategoriesRepositoryImpl
import com.example.myrecipeapp.data.repositoryImpl.RecipeFilterRepositoryImpl
import com.example.myrecipeapp.data.repositoryImpl.RecipeRepositoryImpl
import com.example.myrecipeapp.domain.repository.CategoriesRepository
import com.example.myrecipeapp.domain.repository.RecipeFilterRepository
import com.example.myrecipeapp.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMealRepository(apiService: ApiService): RecipeRepository {
        return RecipeRepositoryImpl(apiService)
    }
    @Provides
    fun provideCategoriesRepository(apiService: ApiService): CategoriesRepository {
        return CategoriesRepositoryImpl(apiService)
    }
//    @Provides
//    fun provideRecipeFilterRepository(apiService: ApiService): RecipeFilterRepository {
//        return RecipeFilterRepositoryImpl(apiService)
//    }
}