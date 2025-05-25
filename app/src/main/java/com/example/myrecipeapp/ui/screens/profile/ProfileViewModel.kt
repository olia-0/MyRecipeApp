package com.example.myrecipeapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.domain.model.UserProfile
import com.example.myrecipeapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    val userProfile = userPreferences.userProfile.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        UserProfile("", "", "", emptyList())
    )

    fun updateAllergies(newAllergies: List<String>) {
        viewModelScope.launch {
            val profile = userProfile.value
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                userRepository.saveProfile(firebaseUser, profile.username, newAllergies)
                userPreferences.saveUserProfile(
                    profile.copy(allergies = newAllergies)
                )
            }
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            val profile = userProfile.value
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                userRepository.saveProfile(firebaseUser, name, profile.allergies)
                userPreferences.saveUserProfile(
                    profile.copy(username = name)
                )
            }
        }
    }
}
