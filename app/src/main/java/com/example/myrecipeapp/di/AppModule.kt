package com.example.myrecipeapp.di

import android.content.Context
import androidx.room.Room
import com.example.myrecipeapp.data.local.RecipeDatabase
import com.example.myrecipeapp.data.local.dao.SavedRecipeDao
import com.example.myrecipeapp.data.local.dao.ViewedRecipeDao
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMealRepository(
        apiService: ApiService,
        daoSaved: SavedRecipeDao,
        daoViewed: ViewedRecipeDao
    ): RecipeRepository {
        return RecipeRepositoryImpl(apiService, daoSaved, daoViewed)
    }
    @Provides
    fun provideCategoriesRepository(apiService: ApiService): CategoriesRepository {
        return CategoriesRepositoryImpl(apiService)
    }


//    @Provides
//    fun provideRecipeFilterRepository(apiService: ApiService): RecipeFilterRepository {
//        return RecipeFilterRepositoryImpl(apiService)
//    }

    //////Room
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            "recipe_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSavedRecipeDao(db: RecipeDatabase): SavedRecipeDao = db.savedRecipeDao()
    @Provides
    fun provideViewedRecipeDao(db: RecipeDatabase): ViewedRecipeDao = db.viewedRecipeDao()

}