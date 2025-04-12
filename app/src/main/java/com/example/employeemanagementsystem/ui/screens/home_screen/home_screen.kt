package com.example.employeemanagementsystem.ui.screens.home_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.dummyEmployeeList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,

) {
    var searchState by remember { mutableStateOf("") }
    val chipOptions = List(15) { "Chip ${it + 1}" }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val employeeList: List<Employee> = dummyEmployeeList
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = Color.Yellow,
                    actionIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    scrolledContainerColor = Color.Transparent,
                    navigationIconContentColor = Color.Transparent
                ),
                actions = { CustomMenu() },
                title = {
                    Text("Employee Manager")
                },

            )
        },
        content = {
            innerPadding ->  Column(modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp)
            .fillMaxSize()) {

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
                chipOptions.forEachIndexed { index, label ->
                    CustomFilterChip(
                        onClick = {selectedIndex = index},
                        index = index,
                        selectedIndex = selectedIndex,
                        label = label,
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(employeeList) { employee ->
                    CustomCard(employee)
                }
            }

        }

        },
        floatingActionButton = {
            CustomFloatingButton(
                onClick = { navController.navigate("add_edit") },
                icon = Icons.Filled.Add
            )
        },
    )
}
