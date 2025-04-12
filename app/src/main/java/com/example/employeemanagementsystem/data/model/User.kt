package com.example.employeemanagementsystem.data.model

data class User(
    val id: Int? = null,
    val email: String,
    val password: String,
    val name: String
){
    override fun toString(): String {
        return "User(id='$id', email='$email', password='$password', name='$name')"
    }

}