package com.example.employeemanagementsystem.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CustomFloatingButton(onClick: () -> Unit, icon: ImageVector) {
    Box (modifier = Modifier.fillMaxSize()){
        FloatingActionButton(
            containerColor = Color.Yellow,

            onClick = { onClick()},
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Icon(icon, "Floating action button.")
        }
    }
}
