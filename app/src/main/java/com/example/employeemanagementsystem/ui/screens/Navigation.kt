package com.example.employeemanagementsystem.ui.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.employeemanagementsystem.ui.screens.auth_screen.LoginScreen
import com.example.employeemanagementsystem.ui.screens.auth_screen.RegisterScreen
import com.example.employeemanagementsystem.ui.screens.employee_form_screen.EmployeeScreen

import com.example.employeemanagementsystem.ui.screens.home_screen.HomeScreen
import com.example.employeemanagementsystem.ui.screens.splash_screen.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("home") { HomeScreen(navController = navController) }
        composable("add_edit") { EmployeeScreen (employee = null, navController = navController) }

        composable("login") { LoginScreen (navController = navController) }
        composable("register") { RegisterScreen (navController = navController) }

    }

}
