package com.example.employeemanagementsystem.di

import com.example.employeemanagementsystem.data.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object AppModule {

//    @Provides
//    fun provideBaseUrl() = "https://67f8a92a2466325443ed4f04.mockapi.io/api/v1/"
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(BASE_URL: String): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//    @Provides
//    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
