package com.example.myrecipeapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myrecipeapp.domain.model.UserProfile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.userDataStore

    private val uidKey = stringPreferencesKey("uid")
    private val emailKey = stringPreferencesKey("email")
    private val usernameKey = stringPreferencesKey("username")
    private val allergiesKey = stringSetPreferencesKey("allergies")
    private val loggedInOnceKey = booleanPreferencesKey("logged_in_once")

    suspend fun saveUserProfile(profile: UserProfile) {
        dataStore.edit { prefs ->
            prefs[uidKey] = profile.uid
            prefs[emailKey] = profile.email
            prefs[usernameKey] = profile.username
            prefs[allergiesKey] = profile.allergies.toSet()
            prefs[loggedInOnceKey] = true
        }
    }

    val userProfile: Flow<UserProfile> = dataStore.data.map { prefs ->
        UserProfile(
            uid = prefs[uidKey] ?: "",
            email = prefs[emailKey] ?: "",
            username = prefs[usernameKey] ?: "",
            allergies = prefs[allergiesKey]?.toList() ?: emptyList()
        )
    }

    val isLoggedIn: Flow<Boolean> = dataStore.data.map { prefs ->
        prefs[loggedInOnceKey] ?: false
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}


//class UserPreferences @Inject constructor(
//    @ApplicationContext private val context: Context
//) {
//
//
//    private val uidKey = stringPreferencesKey("uid")
//    private val emailKey = stringPreferencesKey("email")
//    private val usernameKey = stringPreferencesKey("username")
//    private val allergiesKey = stringSetPreferencesKey("allergies")
//    private val loggedInOnceKey = booleanPreferencesKey("logged_in_once")
//
//    private val dataStore = context.dataStore
//
//    suspend fun saveUserProfile (profile: UserProfile) {
//        dataStore.edit { prefs ->
//            prefs[uidKey] = profile.uid
//            prefs[emailKey] = profile.email
//            prefs[usernameKey] = profile.username
//            prefs[allergiesKey] = profile.allergies.toSet()
//            prefs[loggedInOnceKey] = true
//        }
//    }
//
//    val userProfile: Flow<UserProfile> = dataStore.data.map { prefs ->
//        UserProfile(
//            uid = prefs[uidKey] ?: "",
//            email = prefs[emailKey] ?: "",
//            username = prefs[usernameKey] ?: "",
//            allergies = prefs[allergiesKey]?.toList() ?: emptyList()
//        )
//    }
//
//    val isLoggedIn: Flow<Boolean> = dataStore.data.map { prefs ->
//        prefs[loggedInOnceKey] ?: false
//    }
//
//    suspend fun clear() {
//        dataStore.edit { it.clear() }
//    }
//}
