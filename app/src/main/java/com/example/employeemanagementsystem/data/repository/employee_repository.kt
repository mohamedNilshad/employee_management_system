package com.example.employeemanagementsystem.data.repository

import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.data.remote.ApiService


interface EmployeeRepository {
    suspend fun fetchEmployee(): List<Employee>?
    suspend fun getEmployee(employeeId: Int): Employee?
    suspend fun addEmployee(employee: Employee): Employee?
    suspend fun updateEmployee(employee: Employee): Employee
    suspend fun deleteEmployee(employeeId: Int): Boolean?

}

class EmployeeRepositoryImpl(private val apiService: ApiService) : EmployeeRepository {
    override suspend fun fetchEmployee(): List<Employee> {
        val employeeList = apiService.getEmployees()
        return employeeList
    }

    override suspend fun getEmployee(employeeId: Int): Employee {
        val employee = apiService.getEmployee(employeeId.toString())
        return employee
    }

    override suspend fun addEmployee(employee: Employee): Employee {
        val employeeResult = apiService.addEmployee(employee)
        return employeeResult
    }

    override suspend fun updateEmployee(employee: Employee): Employee {
        val employeeResult = apiService.updateEmployee(employee.id.toString(), employee)
        return employeeResult
    }

    override suspend fun deleteEmployee(employeeId: Int): Boolean {
        val employeeResult = apiService.deleteEmployee(employeeId.toString())
        return employeeResult != null
    }

}

