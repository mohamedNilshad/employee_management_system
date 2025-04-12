package com.example.employeemanagementsystem.ui.screens.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.employeemanagementsystem.data.model.Employee
import com.example.employeemanagementsystem.ui.screens.components.*


@Composable
fun HomeScreen(
    navController: NavHostController,
    employeeList: List<Employee>,
) {
    var searchState by remember { mutableStateOf("") }
    val chipOptions = List(15) { "Chip ${it + 1}" }
    var selectedIndex by remember { mutableIntStateOf(-1) }

    CustomFloatingButton(
        onClick = { navController.navigate("add_edit")},
        Icons.Filled.Add
    )

    Column(modifier = Modifier.padding(8.dp).fillMaxSize()) {

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
}

