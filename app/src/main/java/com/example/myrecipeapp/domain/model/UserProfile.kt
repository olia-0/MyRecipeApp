package com.example.myrecipeapp.domain.model

data class UserProfile(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val allergies: List<String> = emptyList()

)
