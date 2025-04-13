package com.example.employeemanagementsystem.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.employeemanagementsystem.data.model.Employee

@Composable
fun CustomCard(
    employee: Employee,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
){
    Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {

        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (verticalArrangement = Arrangement.Center){
                Text(
                    "Name: ${employee.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    "Department: ${employee.department}",
                    fontSize = 18.sp
                )
                Text(
                    "Email: ${employee.email}",
                    fontSize = 18.sp
                )
            }
            Row {
                IconButton(onClick = { onClickEdit() }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }

                IconButton(onClick = {onClickDelete()}) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }

}