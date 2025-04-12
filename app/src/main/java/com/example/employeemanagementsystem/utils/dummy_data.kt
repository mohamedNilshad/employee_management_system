package com.example.employeemanagementsystem.utils

import com.example.employeemanagementsystem.data.model.Department
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.data.model.User

val dummyEmployeeList = listOf(
    Employee(1, "John Doe", "john@gmail.com", "Development"),
    Employee(2, "Jane Smith", "jane@gmail.com", "Customer Support"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA"),
    Employee(3, "Mike Johnson", "mike@gmail.com", "QA")
)

val dummyDepartmentList: List<Department> = listOf(
    Department(1, "Development"),
    Department(2, "Customer Support"),
    Department(3, "QA")
)

val dummyUserList: List<User> = listOf(
    User(1, "a@gmail.com", name = "aaaa", password = "1234"),
    User(2, "b@gmail.com", name = "bbbb", password = "1234"),
    User(3, "c@gmail.com", name = "cccc", password = "1234"),
    User(4, "d@gmail.com", name = "dddd", password = "1234"),
)