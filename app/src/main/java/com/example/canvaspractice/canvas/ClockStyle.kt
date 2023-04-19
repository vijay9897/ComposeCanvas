package com.example.canvaspractice.canvas

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ClockStyle(
    val minuteIndicatorColor: Color = Color.Gray,
    val hourIndicatorColor: Color = Color.Black,
    val secondHandColor: Color = Color.Red,
    val minuteHandColor: Color = Color.Black,
    val hourHandColor: Color = Color.Black,
    val minuteIndicatorHeight: Dp =  15.dp,
    val hourIndicatorHeight: Dp = 25.dp
)
