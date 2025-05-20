package com.example.myrecipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity
import kotlinx.coroutines.flow.Flow

//@Dao
//interface ViewedRecipeDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertViewedRecipe(recipe: ViewedRecipeEntity)
//
//    @Query("SELECT * FROM viewed_recipes ORDER BY viewedAt DESC")
//    fun getViewedRecipes(): Flow<List<ViewedRecipeEntity>>
//
//    @Query("DELETE FROM viewed_recipes WHERE viewedAt < :cutoffTime")
//    suspend fun deleteOldRecipes(cutoffTime: Long)
//
//    @Query("""
//        DELETE FROM viewed_recipes WHERE id IN (
//            SELECT id FROM viewed_recipes
//            ORDER BY viewedAt ASC
//            LIMIT (SELECT COUNT(*) - :maxCount FROM viewed_recipes)
//        )
//    """)
//    suspend fun trimToSize(maxCount: Int)
//}
@Dao
interface ViewedRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(viewedRecipe: ViewedRecipeEntity)

    @Query("SELECT * FROM viewed_recipes ORDER BY viewedAt DESC")
    fun getAllViewedRecipes(): Flow<List<ViewedRecipeEntity>>

    @Query("SELECT * FROM viewed_recipes WHERE id = :id")
    suspend fun getById(id: String): ViewedRecipeEntity?

    @Query("DELETE FROM viewed_recipes WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM viewed_recipes")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM viewed_recipes")
    suspend fun getCount(): Int

    @Query("SELECT * FROM viewed_recipes ORDER BY viewedAt ASC LIMIT :limit")
    suspend fun getOldestRecipes(limit: Int): List<ViewedRecipeEntity>
}
