package com.example.employeemanagementsystem.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomFilterChip(
    selectedIndex: Int,
    index: Int,
    label: String,
    onClick: (Int) -> Unit
) {
    FilterChip(
        onClick = { onClick(index) },
        label = { Text(label) },
        selected = selectedIndex == index,
        modifier = Modifier.padding(end = 8.dp),
        leadingIcon = if (selectedIndex == index) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Selected",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )

}