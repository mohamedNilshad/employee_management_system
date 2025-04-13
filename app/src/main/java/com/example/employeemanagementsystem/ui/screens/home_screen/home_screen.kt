package com.example.employeemanagementsystem.ui.screens.home_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.RetrofitInstance
import com.example.employeemanagementsystem.data.local.LocalDb
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.dummyDepartmentList
import com.example.employeemanagementsystem.viewmodel.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(RetrofitInstance.api)),
    employeeViewModel: EmployeeViewModel = viewModel(factory = EmployeeViewModelFactory(RetrofitInstance.api))
) {
    var searchState by remember { mutableStateOf("") }
    val chipOptions = dummyDepartmentList
    var selectedIndex by remember { mutableIntStateOf(-1) }

    val context = LocalContext.current

    val logoutResult by authViewModel.logoutResult.collectAsState()
    val employeeResult by employeeViewModel.employeeResult.collectAsState()
    val deleteEmployeeResult by employeeViewModel.deleteEmployeeResult.collectAsState()

    var isLogOuting = false
    var isDeleting = false

    var employeeList: List<Employee> = emptyList()

    val isRefreshing = employeeResult is EmployeeResult.Loading

    val prefHelper = remember { LocalDb(context) }

    LaunchedEffect(Unit) { employeeViewModel.fetchEmployees() }

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
                    actionIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Transparent
                ),
                actions = { CustomMenu(authViewModel, context, prefHelper.getUsername()) },
                title = {
                    Text("Employee Manager")
                },

            )
        },
        content = {
            innerPadding ->  SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = { employeeViewModel.fetchEmployees() }
        ) {
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()) {

                if(isLogOuting || isDeleting){
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }


                CustomTextField(
                    "Search",
                    searchState,
                    onChange = { searchState = it }
                )


                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    chipOptions.forEachIndexed { index, dep ->
                        CustomFilterChip(
                            onClick = { selectedIndex = index },
                            index = index,
                            selectedIndex = selectedIndex,
                            label = dep.department,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                when (employeeResult) {
                    is EmployeeResult.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(50.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                    }

                    is EmployeeResult.Success -> {
                        employeeList = ((employeeResult as EmployeeResult.Success).data as List<Employee>)
                    }

                    is EmployeeResult.Error -> {
                        val message = (employeeResult as EmployeeResult.Error).message

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = message, color = Color.Red)
                        }

                        LaunchedEffect(Unit) {
                            employeeViewModel.resetEmployeeResult()
                        }
                    }

                    is EmployeeResult.Empty -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No employees found.")
                        }
                    }
                }

                if (employeeList.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Empty")
                    }
                } else {
                    LazyColumn {
                        items(employeeList.reversed()) { employee ->
                            CustomCard(
                                employee,
                                onClickEdit = {
                                    navController.navigate("add_edit?employeeId=${employee.id}")
                                },
                                onClickDelete = {
                                    isDeleting = true
                                    employeeViewModel.deleteEmployeeById(employee.id!!)
                                }
                            )
                        }
                    }
                }


                when (deleteEmployeeResult) {
                    is EmployeeResult.Loading -> {isDeleting = true}

                    is EmployeeResult.Success -> {
                        val message = (deleteEmployeeResult as EmployeeResult.Success).message
                        LaunchedEffect(message) {
                            scope.launch { snackbarHostState.showSnackbar(message) }

                            employeeViewModel.resetDeleteEmployeeResult()

                            employeeViewModel.fetchEmployees()
                            isDeleting = false
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
        }

        },
        floatingActionButton = {
            CustomFloatingButton(
                onClick = {
                    navController.navigate("add_edit")
                },
                icon = Icons.Filled.Add
            )
        },
    )

    when (logoutResult) {

        is AuthResult.Loading -> { isLogOuting = true}

        is AuthResult.Success -> {
            isLogOuting = false
            val user = (logoutResult as AuthResult.Success).data
            LaunchedEffect(user) {
                navController.navigate("login"){ popUpTo(0) }
            }
        }

        is AuthResult.Error -> {
            isLogOuting = false
            val message = (logoutResult as AuthResult.Error).message
            LaunchedEffect(message) {
                authViewModel.resetLogoutResult()
            }
        }

        is AuthResult.Empty -> {
            isLogOuting = false
        }
    }
}


@Composable
fun CustomMenu(viewModel: AuthViewModel, context: Context, username: String?) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            DropdownMenuItem(
                text = { Text(username!!) },
                leadingIcon = {Icon(Icons.Filled.AccountCircle, contentDescription = "Name")},
                onClick = {}
            )
            DropdownMenuItem(
                text = { Text("Dark Mode") },
                leadingIcon = {Icon(Icons.Filled.Info, contentDescription = "Theme")},
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("Logout") },
                leadingIcon = {Icon(Icons.Filled.Close, contentDescription = "Logout")},
                onClick = {
                    viewModel.logout(context)
                }
            )
        }

    }
}