package com.example.myrecipeapp.data.datastore

import android.content.Context

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "user_settings")

class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    companion object {
        private val MAX_RECIPES_KEY = intPreferencesKey("max_saved_recipes")
        //private val MAX_VIEWED_RECIPES = intPreferencesKey("max_viewed_recipes")
        private val MAX_IMAGE_MB_KEY = intPreferencesKey("max_image_size_mb")

        const val DEFAULT_MAX_RECIPES = 100
        const val DEFAULT_MAX_SAVED_RECIPES = 100
        const val DEFAULT_MAX_VIEWED_RECIPES = 100
        const val DEFAULT_MAX_IMAGE_MB = 50
    }

    private val MAX_VIEWED_RECIPES_KEY = intPreferencesKey("max_viewed_recipes")
    val maxViewedRecipes: Flow<Int> = context.dataStore.data.map {
        it[MAX_VIEWED_RECIPES_KEY] ?: 100
    }


    val maxSavedRecipes: Flow<Int> = context.dataStore.data.map {
        it[MAX_RECIPES_KEY] ?: DEFAULT_MAX_RECIPES
    }

    val maxImageSizeMB: Flow<Int> = context.dataStore.data.map {
        it[MAX_IMAGE_MB_KEY] ?: DEFAULT_MAX_IMAGE_MB
    }

    suspend fun setMaxSavedRecipes(value: Int) {
        context.dataStore.edit { it[MAX_RECIPES_KEY] = value }
    }

    suspend fun setMaxImageSizeMB(value: Int) {
        context.dataStore.edit { it[MAX_IMAGE_MB_KEY] = value }
    }
}