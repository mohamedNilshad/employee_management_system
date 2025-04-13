package com.example.employeemanagementsystem


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.employeemanagementsystem.ui.screens.AppNavigation


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        setContent {
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}