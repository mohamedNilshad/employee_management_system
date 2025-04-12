package com.example.employeemanagementsystem.ui.screens.employee_form_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.model.*
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.dummyDepartmentList
import com.example.employeemanagementsystem.utils.dummyEmployeeList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(employee: Employee? = null, navController: NavHostController) {
    var nameState by remember { mutableStateOf(employee?.name ?: "") }
    val options: List<Department> = dummyDepartmentList
    var selected by remember { mutableStateOf(options.first().department) }
    val title: String = if (employee == null) "Add New Employee" else "Update Employee"
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val tempEmployeeList: List<Employee> = dummyEmployeeList

    Scaffold(
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

            CustomTextField("Name", nameState, onChange = { nameState = it })

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
//                onSave(name, department)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (employee == null) "Add" else "Update")
            }

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(tempEmployeeList) { employee ->
                    CustomCard(employee)
                }
            }


        }

        },
    )
}






