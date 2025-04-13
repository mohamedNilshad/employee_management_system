package com.example.employeemanagementsystem.viewmodel

import androidx.lifecycle.*
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.data.repository.EmployeeRepository

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class EmployeeResult {
    object Loading : EmployeeResult()
    data class Success(
        val status: Boolean,
        val message: String,
        val data:Any
    ) : EmployeeResult()
    data class Error(val message: String) : EmployeeResult()
    object Empty : EmployeeResult()
}



class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {
    private val _employeeResult = MutableStateFlow<EmployeeResult>(EmployeeResult.Empty)

    val employeeResult: StateFlow<EmployeeResult> = _employeeResult

    fun resetEmployeeResult() {
        _employeeResult.value = EmployeeResult.Empty
    }


    fun fetchEmployees() {

        viewModelScope.launch {
            _employeeResult.value = EmployeeResult.Loading
            try {
                val employeeList = repository.fetchEmployee()
                if (employeeList != null) {
                    _employeeResult.value = EmployeeResult.Success(status = true, message = "success", data = employeeList)
                } else {
                    _employeeResult.value = EmployeeResult.Error("Empty List")
                }
            } catch (e: Exception) {
                _employeeResult.value = EmployeeResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addEmployee(employee: Employee) {

        viewModelScope.launch {
            _employeeResult.value = EmployeeResult.Loading
            try {
                val isAdded = repository.addEmployee(employee)

                if (isAdded) {
                    _employeeResult.value = EmployeeResult.Success(status = true, message = "New Employee Added Success!!", data = "")
                } else {
                    _employeeResult.value = EmployeeResult.Error("New Employee Adding Failed!")
                }
            } catch (e: Exception) {
                _employeeResult.value = EmployeeResult.Error(e.message ?: "Unknown error")
            }
        }
    }

}



