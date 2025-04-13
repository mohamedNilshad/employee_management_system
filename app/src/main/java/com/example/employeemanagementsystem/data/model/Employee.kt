package com.example.employeemanagementsystem.data.model

data class Employee(
    val id: Int? = null,
    val name: String = "",
    val email: String = "",
    val department: String = "",
){
    companion object {
        fun empty() = Employee(-1, "", "","")
    }
}