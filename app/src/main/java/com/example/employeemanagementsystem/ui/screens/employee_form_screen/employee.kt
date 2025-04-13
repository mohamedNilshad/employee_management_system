package com.example.employeemanagementsystem.ui.screens.employee_form_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.RetrofitInstance
import com.example.employeemanagementsystem.data.model.*
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.dummyDepartmentList
import com.example.employeemanagementsystem.viewmodel.EmployeeResult
import com.example.employeemanagementsystem.viewmodel.EmployeeViewModel
import com.example.employeemanagementsystem.viewmodel.EmployeeViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(
    employee: Employee? = null,
    navController: NavHostController,
    employeeViewModel: EmployeeViewModel = viewModel(factory = EmployeeViewModelFactory(RetrofitInstance.api))
) {
    var nameState by remember { mutableStateOf(employee?.name ?: "") }
    var emailState by remember { mutableStateOf(employee?.email ?: "") }
    val options: List<Department> = dummyDepartmentList

    var selected by remember { mutableStateOf(options.first().department) }

    val title: String = if (employee == null) "Add New Employee" else "Update Employee"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val tempEmployeeList = mutableListOf<Employee>()
    var tempEmployee: Employee = Employee.empty()

    val employeeResult by employeeViewModel.employeeResult.collectAsState()
    var isLoading = false
    var isEnable = false

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
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color.Yellow,
                    actionIconContentColor = Color.Transparent,
                    titleContentColor = Color.Black,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Black
                ),
                title = { Text(title) },

                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        },
        content = { innerPadding ->  Column(modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
            .fillMaxSize()) {

            CustomTextField("Name", nameState, onChange = {
                nameState = it
                isEnable = (emailState != "") && (nameState != "")
            })
            Spacer(Modifier.height(8.dp))

            CustomTextField("Email", emailState, onChange = {
                emailState = it
                isEnable = (emailState != "") && (nameState != "")
            })
            Spacer(Modifier.height(8.dp))

            CustomDropdownField(
                label = "Select Option",
                options = options,
                selectedOption = selected,
                onOptionSelected = { selected = it }
            )
            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    tempEmployee = Employee(name = nameState, email = emailState, department = selected)
                    employeeViewModel.addEmployee (tempEmployee)
                },
                modifier = Modifier.fillMaxWidth(),
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
                    Text(if (employee == null) "Add" else "Update")
                }

            }

            Spacer(modifier = Modifier.height(8.dp))
            when (employeeResult) {
                is EmployeeResult.Loading -> { isLoading = true }

                is EmployeeResult.Success -> {
                    val isAdded = (employeeResult as EmployeeResult.Success).status
                    val message = (employeeResult as EmployeeResult.Success).message
                    if (isAdded) {
                        tempEmployeeList.add(tempEmployee)
                    }
                    LaunchedEffect(message) {
                        scope.launch { snackbarHostState.showSnackbar(message) }
                        employeeViewModel.resetEmployeeResult()
                    }
                    isLoading = false
                    isEnable = true
                }

                is EmployeeResult.Error -> {

                    val message = (employeeResult as EmployeeResult.Error).message
                    LaunchedEffect(message) {
                        scope.launch { snackbarHostState.showSnackbar(message) }
                        employeeViewModel.resetEmployeeResult()
                        isLoading = false
                        isEnable = true
                    }
                }

                is EmployeeResult.Empty -> {
                    isLoading = false
                    isEnable = true
                }
            }

            if(tempEmployeeList.isEmpty()){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No new employees added.")
                }
            }else {
                LazyColumn {
                    items(tempEmployeeList.reversed()) { employee ->
                        CustomCard(employee)
                    }
                }
            }

        }

        },
    )
}






