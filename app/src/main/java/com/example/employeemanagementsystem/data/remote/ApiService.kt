package com.example.employeemanagementsystem.data.remote

import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.data.model.User
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("employees")
    suspend fun getEmployees(): List<Employee>

    @POST("employees")
    suspend fun addEmployee(@Body employee: Employee): Employee

    @PUT("employees/{id}")
    suspend fun updateEmployee(@Path("id") id: String, @Body employee: Employee): Employee

    @DELETE("employees/{id}")
    suspend fun deleteEmployee(@Path("id") id: String)

    @POST("users")
    suspend fun register(@Body user: User): User

    @GET("users")
    suspend fun login(): List<User>

}
