package com.example.myrecipeapp.di

import com.example.myrecipeapp.data.remote.api.ApiService
import com.example.myrecipeapp.data.repositoryImpl.MealRepositoryImpl
import com.example.myrecipeapp.domain.repository.MealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMealRepository(apiService: ApiService): MealRepository {
        return MealRepositoryImpl(apiService)
    }
}