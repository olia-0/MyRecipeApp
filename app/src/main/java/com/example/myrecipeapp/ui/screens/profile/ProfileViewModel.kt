package com.example.myrecipeapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.UserProfile
import com.example.myrecipeapp.domain.repository.UserRepository
import com.example.myrecipeapp.domain.usecase.GetSavedRecipesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository,
    private val getSavedRecipesUseCase: GetSavedRecipesUseCase,
) : ViewModel() {


    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes: StateFlow<List<Recipe>> = _savedRecipes.asStateFlow()

    private val _userProfile = MutableStateFlow(UserProfile("", "", "", emptyList()))
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

//    val userProfile = userPreferences.userProfile.stateIn(
//        viewModelScope,
//        SharingStarted.WhileSubscribed(5000),
//        UserProfile("", "", "", emptyList())
//    )


    init {
        fetchUserProfile()
        fetchSavedRecipes()
    }
    private fun fetchUserProfile() {
        viewModelScope.launch {
            userPreferences.userProfile.collect { profile ->
                _userProfile.value = profile
            }
        }
    }

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

    fun updateUser(name: String, newAllergies: List<String>) {
        viewModelScope.launch {
            val profile = userProfile.value
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                userRepository.saveProfile(firebaseUser, name, newAllergies)
                userPreferences.saveUserProfile(
                    profile.copy(username = name, allergies = newAllergies)
                )
            }
        }
    }
    fun updateName1(name: String) {
        viewModelScope.launch {
            val profile = userProfile.value
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                userRepository.saveProfile(firebaseUser, name, profile.allergies)
//                userPreferences.saveUserProfile(
//                    profile.copy(username = name)
//                )
                //userPreferences.saveUserProfile(updatedProfile)

                val updatedProfile = profile.copy(username = name)
                userPreferences.saveUserProfile(updatedProfile)


                // 🔁 ОНОВЛЕННЯ СТРИМУ
                _userProfile.value = updatedProfile
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

    private fun fetchSavedRecipes() {
        viewModelScope.launch {
            getSavedRecipesUseCase().collect { recipes ->
                _savedRecipes.value = recipes
            }
        }
    }
}
