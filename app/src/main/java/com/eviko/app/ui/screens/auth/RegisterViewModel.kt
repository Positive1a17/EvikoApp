package com.eviko.app.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eviko.app.data.model.User
import com.eviko.app.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Initial)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun register(email: String, password: String, name: String? = null) {
        viewModelScope.launch {
            _uiState.value = RegisterUiState.Loading
            
            userRepository.registerUser(email, password, name)
                .onSuccess { user ->
                    _uiState.value = RegisterUiState.Success(user)
                }
                .onFailure { error ->
                    _uiState.value = RegisterUiState.Error(error.message ?: "Unknown error")
                }
        }
    }
}

sealed class RegisterUiState {
    object Initial : RegisterUiState()
    object Loading : RegisterUiState()
    data class Success(val user: User) : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
} 