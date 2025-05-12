package com.example.myrecipeapp.di

import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.data.repositoryImpl.RecipeRepositoryImpl
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
}