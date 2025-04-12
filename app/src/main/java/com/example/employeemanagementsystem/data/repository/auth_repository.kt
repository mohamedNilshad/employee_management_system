package com.example.employeemanagementsystem.data.repository

import com.example.employeemanagementsystem.data.model.User
import com.example.employeemanagementsystem.data.remote.ApiService


interface AuthRepository {
    suspend fun login(email: String, password: String): User?

    suspend fun registration(newUser: User): Boolean?
}

class AuthRepositoryImpl(private val apiService: ApiService) : AuthRepository {
    override suspend fun login(email: String, password: String): User? {
        val users = apiService.login()
        return users.find { it.email == email && it.password == password }
    }

    override suspend fun registration(newUser: User): Boolean {
        val user = apiService.register(user = newUser)
        return user != null
    }
}

