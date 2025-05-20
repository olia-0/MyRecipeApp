package com.example.myrecipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.data.local.SavedRecipeEntity2
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: SavedRecipeEntity)

    @Delete
    suspend fun delete(recipe: SavedRecipeEntity)

    @Query("SELECT * FROM saved_recipes")
    fun getAll(): Flow<List<SavedRecipeEntity>>

    @Query("SELECT * FROM saved_recipes WHERE id = :id")
    suspend fun getById(id: String): SavedRecipeEntity?

    @Query("DELETE FROM saved_recipes WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM saved_recipes")
    suspend fun getCount(): Int

    @Query("SELECT SUM(imageSize) FROM saved_recipes")
    suspend fun getTotalSize(): Long

//    @Query("SELECT * FROM saved_recipes ORDER BY lastModified ASC LIMIT 1")
//    suspend fun getOldest(): SavedRecipeEntity?

//    @Query("DELETE FROM saved_recipes WHERE id IN (SELECT id FROM saved_recipes ORDER BY id LIMIT 1)")
//    suspend fun deleteOldest()


}
