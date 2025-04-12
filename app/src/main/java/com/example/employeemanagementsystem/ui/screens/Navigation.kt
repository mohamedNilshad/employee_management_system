package com.example.employeemanagementsystem.ui.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.ui.screens.employee_form_screen.EmployeeScreen

import com.example.employeemanagementsystem.ui.screens.home_screen.HomeScreen
import com.example.employeemanagementsystem.ui.screens.splash_screen.SplashScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("employeeList", it)
                    navController.navigate("home")
                }
            )
        }
        composable("home") {
            val employeeList = navController.previousBackStackEntry
                ?.savedStateHandle?.get<List<Employee>>("employeeList")
            HomeScreen(
                navController = navController,
                employeeList ?: emptyList(),
            )
        }
        composable("add_edit") { EmployeeScreen (null) }

    }

}
