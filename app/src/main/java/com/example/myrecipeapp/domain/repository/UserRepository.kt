package com.example.myrecipeapp.domain.repository

import com.google.firebase.auth.FirebaseUser

// domain/repository/UserRepository.kt
interface UserRepository {
    suspend fun saveProfile(user: FirebaseUser, username: String, allergies: List<String>)
    suspend fun loadAndCacheUser(uid: String)
}
