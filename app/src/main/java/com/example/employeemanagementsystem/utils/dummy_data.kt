package com.example.employeemanagementsystem.utils

import com.example.employeemanagementsystem.data.model.Department
import com.example.employeemanagementsystem.data.model.Employee

val dummyEmployeeList = listOf(
    Employee(1, "John Doe", "john@gmail.com", "Development"),
    Employee(2, "Jane Smith", "jane@gmail.com", "Customer Support"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA")
)

val dummyDepartmentList: List<Department> = listOf(
    Department(1, "Development"),
    Department(2, "Customer Support"),
    Department(3, "QA")
)