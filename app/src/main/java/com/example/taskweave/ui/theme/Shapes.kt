package com.example.taskweave.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),   // For buttons, text fields, chips
    medium = RoundedCornerShape(12.dp), // For standard cards and dialogs
    large = RoundedCornerShape(16.dp)   // For bottom sheets or large featured cards
)