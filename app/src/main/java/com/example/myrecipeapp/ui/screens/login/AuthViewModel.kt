package com.example.myrecipeapp.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.data.datastore.UserPreferences
import com.example.myrecipeapp.domain.model.UserProfile
import com.example.myrecipeapp.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class AuthViewModel @Inject constructor() : ViewModel() {
//
//    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
//
//    var currentUser by mutableStateOf<FirebaseUser?>(auth.currentUser)
//        private set
//
//    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                currentUser = auth.currentUser
//                onResult(task.isSuccessful)
//            }
//    }
//
//    fun signUpWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                currentUser = auth.currentUser
//                onResult(task.isSuccessful)
//            }
//    }
//
//    fun signOut() {
//        auth.signOut()
//        currentUser = null
//    }
//}
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    //var currentUser by mutableStateOf<FirebaseUser?>(auth.currentUser)
        //private set
    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()



//    fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    currentUser = auth.currentUser
//                    viewModelScope.launch {
//                        currentUser?.let { user ->
//                            // Зберігаємо у DataStore профіль з мінімальними даними
//                            userPreferences.saveUserProfile(
//                                UserProfile(
//                                    uid = user.uid,
//                                    email = user.email ?: "",
//                                    username = "",  // або можна додати логіку отримання імені
//                                    allergies = emptyList()
//                                )
//                            )
//                        }
//                    }
//                    onResult(true)
//                } else {
//                    onResult(false)
//                }
//            }
//    }
fun signInWithEmail(email: String, password: String, onResult: (Boolean) -> Unit) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _currentUser.value = auth.currentUser
                viewModelScope.launch {
                    currentUser.let { user ->
                        userRepository.loadAndCacheUser(user.value?.uid ?: "")
                    }
                }
                onResult(true)
            } else {
                onResult(false)
            }
        }
}

    fun signUpWithEmail(email: String, password: String,name: String, onResult: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                    viewModelScope.launch {
                        currentUser.let { user ->
                            userPreferences.saveUserProfile(
                                UserProfile(
                                    uid = user.value?.uid ?:"",
                                    email = user.value?.email ?: "",
                                    username = name,
                                    allergies = emptyList()
                                )
                            )
                        }
                    }
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _currentUser.value = null
        viewModelScope.launch {
            userPreferences.clear()
        }
    }
}
