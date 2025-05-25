package com.example.myrecipeapp.data.remote

import android.util.Log
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.domain.model.UserProfile
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// data/remote/UserRemoteDataSource.kt

class UserRemoteDataSource @Inject constructor(

) {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveUserProfileToFirestore(user: FirebaseUser, username: String, allergies: List<String>) {
        val profile = UserProfile(
            uid = user.uid,
            email = user.email ?: "",
            username = username,
            allergies = allergies
        )

        firestore.collection("users")
            .document(user.uid)
            .set(profile)
            .addOnSuccessListener { Log.d("Firestore", "User saved") }
            .addOnFailureListener { Log.e("Firestore", "Error", it) }
    }

    fun fetchAndCacheUser(uid: String, userPrefs: UserPreferences) {
        firestore.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val profile = doc.toObject(UserProfile::class.java)
                profile?.let {
                    CoroutineScope(Dispatchers.IO).launch {
                        userPrefs.saveUserProfile(it)
                    }
                }
            }
    }
}
