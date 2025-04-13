package com.example.employeemanagementsystem.data.repository

import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.data.remote.ApiService


interface EmployeeRepository {
    suspend fun fetchEmployee(): List<Employee>?
    suspend fun addEmployee(employee: Employee): Boolean
}

class EmployeeRepositoryImpl(private val apiService: ApiService) : EmployeeRepository {
    override suspend fun fetchEmployee(): List<Employee> {
        val employeeList = apiService.getEmployees()
        return employeeList
    }

    override suspend fun addEmployee(employee: Employee): Boolean {
        val employeeResult = apiService.addEmployee(employee)
        return employeeResult != null
    }

}

