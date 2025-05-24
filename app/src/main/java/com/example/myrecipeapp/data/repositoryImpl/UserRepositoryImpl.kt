package com.example.myrecipeapp.data.repositoryImpl

import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.data.remote.UserRemoteDataSource
import com.example.myrecipeapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// data/repository/UserRepositoryImpl.kt
class UserRepositoryImpl @Inject constructor(
    private val remote: UserRemoteDataSource,
    private val userPrefs: UserPreferences
) : UserRepository {
    private suspend fun getUserId(): String =
        userPrefs.userProfile.first().uid

    override suspend fun saveProfile(user: FirebaseUser, username: String, allergies: List<String>) {
        remote.saveUserProfileToFirestore(user, username, allergies)
    }

    override suspend fun loadAndCacheUser(uid: String) {
        remote.fetchAndCacheUser(uid, userPrefs)
    }
}
