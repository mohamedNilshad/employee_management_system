package com.example.employeemanagementsystem.viewmodel

import androidx.lifecycle.*
import com.example.employeemanagementsystem.data.model.User
import com.example.employeemanagementsystem.data.repository.AuthRepository

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class AuthResult {
    object Loading : AuthResult()
    data class Success(
        val status: Boolean,
        val message: String,
        val data:Any
    ) : AuthResult()
    data class Error(val message: String) : AuthResult()
    object Empty : AuthResult()
}



class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _loginResult = MutableStateFlow<AuthResult>(AuthResult.Empty)
    private val _registerResult= MutableStateFlow<AuthResult>(AuthResult.Empty)

    val loginResult: StateFlow<AuthResult> = _loginResult
    val registerResult: StateFlow<AuthResult> = _registerResult

    fun resetLoginResult() {
        _loginResult.value = AuthResult.Empty
    }

    fun resetRegResult() {
        _loginResult.value = AuthResult.Empty
    }

    fun login(email: String, password: String) {

        viewModelScope.launch {
            _loginResult.value = AuthResult.Loading
            try {
                val user = repository.login(email, password)
                if (user != null) {
                    _loginResult.value = AuthResult.Success(status = true, message = "success", data = user)
                } else {
                    _loginResult.value = AuthResult.Error("Invalid credentials")
                }
            } catch (e: Exception) {
                _loginResult.value = AuthResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun register(newUser: User) {

        viewModelScope.launch {
            _registerResult.value = AuthResult.Loading
            try {
                val isAdded = repository.registration(newUser)

                if (isAdded == true) {
                    _registerResult.value = AuthResult.Success(status = true, message = "Registration Success!!", data = "")
                } else {
                    _registerResult.value = AuthResult.Error("Registration Failed!")
                }
            } catch (e: Exception) {
                _registerResult.value = AuthResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}



