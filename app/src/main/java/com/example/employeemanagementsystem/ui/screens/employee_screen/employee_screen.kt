package com.example.employeemanagementsystem.ui.screens.employee_screen

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
    employeeId: Int? = null,
    navController: NavHostController,
    employeeViewModel: EmployeeViewModel = viewModel(factory = EmployeeViewModelFactory(RetrofitInstance.api))
) {
    var nameState by remember { mutableStateOf( "") }
    var emailState by remember { mutableStateOf("") }
    val options: List<Department> = dummyDepartmentList

    var selected by remember { mutableStateOf(options.first().department) }

    val title: String = if (employeeId == -1) "Add New Employee" else "Update Employee"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val tempEmployeeList = mutableListOf<Employee>()
    var tempEmployee: Employee

    val employeeResult by employeeViewModel.employeeResult.collectAsState()
    val getEmployeeResult by employeeViewModel.getEmployeeResult.collectAsState()
    val deleteEmployeeResult by employeeViewModel.deleteEmployeeResult.collectAsState()

    var isDeleting = false
    var isLoading = false
    var isEnable = false

    var isGettingEmployee = false
    var inPageEmployeeId = -1


    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if(employeeId != -1) {
        LaunchedEffect(Unit) {
            employeeViewModel.fetchEmployeeById(employeeId!!)
        }
    }

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
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
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

            if(isGettingEmployee || isDeleting){
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

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
                    if(employeeId == -1){
                        if(inPageEmployeeId != -1) {
                            employeeViewModel.updateEmployee(Employee(id = inPageEmployeeId, name = nameState, email = emailState, department = selected))
                        }else{
                            tempEmployee = Employee(name = nameState, email = emailState, department = selected)
                            employeeViewModel.addEmployee(tempEmployee)
                        }

                    }else{
                        employeeViewModel.updateEmployee(Employee(id = employeeId, name = nameState, email = emailState, department = selected))
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = (employeeId != -1) ||(!isLoading && isEnable)
            ) {

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = Color.Blue
                    )
                } else {
                    Text(if (employeeId == -1 && inPageEmployeeId == -1) "Add" else "Update")
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            when (employeeResult) {
                is EmployeeResult.Loading -> { isLoading = true }

                is EmployeeResult.Success -> {
                    val isAdded = (employeeResult as EmployeeResult.Success).status
                    val updatedEmployee = (employeeResult as EmployeeResult.Success).data as Employee
                    val message = (employeeResult as EmployeeResult.Success).message

                    if (isAdded && (employeeId == -1)) {
                        if(inPageEmployeeId != -1){
                            val index = tempEmployeeList.indexOfFirst { it.id == updatedEmployee.id }
                            if (index != -1) {
                                tempEmployeeList[index] = updatedEmployee
                            }
                            println(tempEmployeeList)
                        }else {
                            tempEmployeeList.add(updatedEmployee)
                        }
                        nameState = ""
                        emailState = ""
                        selected = dummyDepartmentList.first().department
                    }
                    LaunchedEffect(message) {
                        scope.launch { snackbarHostState.showSnackbar(message) }
                        employeeViewModel.resetEmployeeResult()
                    }
                    isLoading = false
                    isEnable = false
                    if (employeeId != -1) navController.popBackStack()
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
                        CustomCard(
                            employee=employee,
                            onClickEdit = {
                                nameState = employee.name
                                emailState = employee.email
                                selected = employee.department
                                inPageEmployeeId = employee.id!!
                            },
                            onClickDelete = {
                                employeeViewModel.deleteEmployeeById(employee.id!!)
                            }
                        )
                    }
                }
            }

        }

        },
    )
    when (getEmployeeResult) {
        is EmployeeResult.Loading -> { isGettingEmployee = true }

        is EmployeeResult.Success -> {
            val employee = (getEmployeeResult as EmployeeResult.Success).data as Employee

            nameState = employee.name
            emailState = employee.email
            selected = employee.department
            isGettingEmployee = false
        }

        is EmployeeResult.Error -> {

            val message = (getEmployeeResult as EmployeeResult.Error).message
            LaunchedEffect(message) {
                scope.launch { snackbarHostState.showSnackbar(message) }
                employeeViewModel.resetEmployeeResult()
                isGettingEmployee = false
            }
        }

        is EmployeeResult.Empty -> { isGettingEmployee = false }
    }

    when (deleteEmployeeResult) {
        is EmployeeResult.Loading -> {isDeleting = true}

        is EmployeeResult.Success -> {
            val message = (deleteEmployeeResult as EmployeeResult.Success).message
            LaunchedEffect(message) {
                scope.launch { snackbarHostState.showSnackbar(message) }
                employeeViewModel.resetDeleteEmployeeResult()
                isDeleting = false
                navController.popBackStack()
            }
        }

        is EmployeeResult.Error -> {
            val message = (deleteEmployeeResult as EmployeeResult.Error).message

            LaunchedEffect(Unit) {
                scope.launch { snackbarHostState.showSnackbar(message) }
                employeeViewModel.resetDeleteEmployeeResult()
                isDeleting = false
            }
        }

        is EmployeeResult.Empty -> {isDeleting = false}
    }
}






