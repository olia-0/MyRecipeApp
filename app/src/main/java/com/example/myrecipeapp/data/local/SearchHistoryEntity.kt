package com.example.myrecipeapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey val searchQuery: String,
    val timestamp: Long = System.currentTimeMillis()
)