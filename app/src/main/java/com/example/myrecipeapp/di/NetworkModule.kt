package com.example.myrecipeapp.di

import com.example.myrecipeapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    class NgrokInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("ngrok-skip-browser-warning", "true")
                .addHeader("User-Agent", "ngrok-agent/3.22.1 ngrok-go/1.12.1")
                .build()
            return chain.proceed(newRequest)
        }
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(NgrokInterceptor())  // Додаємо інтерсептор сюди
            .build()
    }
    // Метод для надання OkHttpClient (можна налаштувати за потребою)
//    @Provides
//    @Singleton
//    fun provideOkHttpClient(): OkHttpClient {
//        return OkHttpClient.Builder()
//            .build() // Налаштуй за потребою, додавши інтерсептори чи тайм-аути
//    }


    // Метод для надання Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()

            //.baseUrl("https://www.themealdb.com/api/json/v1/1/")
            //.baseUrl("http://localhost:8080/")
            .baseUrl("https://41dc-109-229-30-29.ngrok-free.app/")

            //.baseUrl("http://192.168.1.100:8080/") http://10.0.2.2:8080/
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