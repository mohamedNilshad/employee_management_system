package com.example.employeemanagementsystem.ui.screens.employee_form_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import com.example.employeemanagementsystem.data.model.*
import com.example.employeemanagementsystem.ui.screens.components.*
import com.example.employeemanagementsystem.utils.dummyDepartmentList

@Composable
fun EmployeeScreen(employee: Employee? = null) {
    var nameState by remember { mutableStateOf(employee?.name ?: "") }
    val options: List<Department> = dummyDepartmentList
    var selected by remember { mutableStateOf(options.first().department) }


    Column(Modifier.padding(16.dp)) {
        Row (
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ){
            IconButton(
                onClick = { }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                "Add New Employee",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(8.dp))

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
    }
}




