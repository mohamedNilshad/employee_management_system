package com.example.employeemanagementsystem.ui.screens.auth_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.RetrofitInstance
import com.example.employeemanagementsystem.data.model.User
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.rememberImeState
import com.example.employeemanagementsystem.viewmodel.AuthResult
import com.example.employeemanagementsystem.viewmodel.AuthViewModel
import com.example.employeemanagementsystem.viewmodel.LoginViewModelFactory
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel(factory = LoginViewModelFactory(RetrofitInstance.api))
) {
    var nameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var passState by remember { mutableStateOf("") }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    val registerResult by viewModel.registerResult.collectAsState()
    var isLoading = false
    var isEnable = false

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Color(0xFF323232),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                )
            }
        },
        content = {innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
            Text(
                "Employee Manager",
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
                color = Color.Blue
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Register",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            CustomTextField("Name", nameState, onChange = {
                nameState = it
                isEnable = (emailState != "") && (nameState != "") && (passState != "")
            })
            Spacer(Modifier.height(8.dp))
            CustomTextField("Email", emailState, onChange = {
                emailState = it
                isEnable = (emailState != "") && (nameState != "") && (passState != "")
            })
            Spacer(Modifier.height(8.dp))
            CustomTextField("Password", passState, onChange = {
                passState = it
                isEnable = (emailState != "") && (nameState != "") && (passState != "")
            })
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
//
                    if(emailState != "" && nameState != "null" && passState != "null"){
                        viewModel.register(User(name = nameState, email = emailState, password = passState))
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = !isLoading && isEnable
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.Blue
                    )
                } else {
                    Text("Create account")
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("or")
            TextButton(onClick = { navController.popBackStack()}) {
                Text("Login")
            }


        }
        },
    )
    when (registerResult) {
        is AuthResult.Loading -> { isLoading = true}

        is AuthResult.Success -> {
            isLoading = false
            isEnable = true
            val isRegistered = (registerResult as AuthResult.Success).status

            LaunchedEffect(isRegistered) {
//                if(isRegistered) navController.navigate("home"){ popUpTo(0) }
            }
        }

        is AuthResult.Error -> {
            isLoading = false
            isEnable = true
            val message = (registerResult as AuthResult.Error).message
            LaunchedEffect(message) {
                scope.launch { snackbarHostState.showSnackbar(message) }
                viewModel.resetRegResult()
            }
        }

        is AuthResult.Empty -> {
            isLoading = false
        }
    }
}






