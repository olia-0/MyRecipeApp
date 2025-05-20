package com.example.myrecipeapp.data.local

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myrecipeapp.domain.repository.RecipeRepository

//class CleanupViewedRecipesWorker(
//    context: Context,
//    workerParams: WorkerParameters,
//    private val repository: RecipeRepository
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//        repository.cleanupOldRecipes()
//        repository.limitStoredRecipes(maxCount = 100)
//        return Result.success()
//    }
//}
