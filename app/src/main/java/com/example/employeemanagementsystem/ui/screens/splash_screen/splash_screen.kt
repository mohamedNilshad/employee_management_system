package com.example.employeemanagementsystem.ui.screens.splash_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.local.LocalDb
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    val context = LocalContext.current
    val prefHelper = remember { LocalDb(context) }

    LaunchedEffect(Unit) {
        val isLoggedIn = prefHelper.isLoggedIn()

        delay(100)
        if(isLoggedIn){
            navController.navigate("home"){
                popUpTo("splash"){inclusive = true}
            }
        }else{
            navController.navigate("login"){
                popUpTo("splash"){inclusive = true}
            }
        }

    }
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Employee Manager", fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}