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
    private val _deleteEmployeeResult = MutableStateFlow<EmployeeResult>(EmployeeResult.Empty)
    private val _getEmployeeResult = MutableStateFlow<EmployeeResult>(EmployeeResult.Empty)

    val employeeResult: StateFlow<EmployeeResult> = _employeeResult
    val deleteEmployeeResult: StateFlow<EmployeeResult> = _deleteEmployeeResult
    val getEmployeeResult: StateFlow<EmployeeResult> = _getEmployeeResult

    fun resetEmployeeResult() {
        _employeeResult.value = EmployeeResult.Empty
    }

    fun resetDeleteEmployeeResult() {
        _deleteEmployeeResult.value = EmployeeResult.Empty
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
                val employeeRs = repository.addEmployee(employee)

                if (employeeRs != null) {
                    _employeeResult.value = EmployeeResult.Success(status = true, message = "New Employee Added Success!!", data = employeeRs)
                } else {
                    _employeeResult.value = EmployeeResult.Error("New Employee Adding Failed!")
                }
            } catch (e: Exception) {
                _employeeResult.value = EmployeeResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateEmployee(employee: Employee) {

        viewModelScope.launch {
            _employeeResult.value = EmployeeResult.Loading
            try {
                val updatedEmployee = repository.updateEmployee(employee)
                _employeeResult.value = EmployeeResult.Success(status = true, message = "Employee Updated Success!!", data = updatedEmployee)
            } catch (e: Exception) {
                _employeeResult.value = EmployeeResult.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteEmployeeById(employeeId: Int) {

        viewModelScope.launch {
            _deleteEmployeeResult.value = EmployeeResult.Loading
            try {
                val isDelete = repository.deleteEmployee(employeeId)
                if (isDelete == true) {
                    _deleteEmployeeResult.value = EmployeeResult.Success(status = true, message = "Deleted Success", data = "")
                } else {
                    _deleteEmployeeResult.value = EmployeeResult.Error("Empty")
                }
            } catch (e: Exception) {
                _deleteEmployeeResult.value = EmployeeResult.Error(e.message ?: "Unknown error")
            }
        }
    }

}



