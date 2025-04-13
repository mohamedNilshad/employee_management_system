package com.example.employeemanagementsystem.viewmodel

import androidx.lifecycle.*
import com.example.employeemanagementsystem.data.remote.ApiService
import com.example.employeemanagementsystem.data.repository.EmployeeRepositoryImpl

class EmployeeViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = EmployeeRepositoryImpl(apiService)
        return EmployeeViewModel(repository) as T
    }
}