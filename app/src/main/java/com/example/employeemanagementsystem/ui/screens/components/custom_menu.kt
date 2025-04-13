package com.example.employeemanagementsystem.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CustomMenu1() {
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
                text = { Text("Nilshad") },
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
                onClick = { }
            )
        }
    }
}