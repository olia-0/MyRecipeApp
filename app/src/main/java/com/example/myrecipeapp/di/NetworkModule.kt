package com.example.myrecipeapp.di

import com.example.myrecipeapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Метод для надання OkHttpClient (можна налаштувати за потребою)
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build() // Налаштуй за потребою, додавши інтерсептори чи тайм-аути
    }

    // Метод для надання Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .client(okHttpClient)
            //.addConverterFactory(MoshiConverterFactory.create()) або GsonConverterFactory.create(), якщо використовуєш Gson
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Метод для надання ApiService
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}