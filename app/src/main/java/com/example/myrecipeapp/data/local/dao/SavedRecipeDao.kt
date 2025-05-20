package com.example.myrecipeapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.data.local.SavedRecipeEntity2
import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
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
}
