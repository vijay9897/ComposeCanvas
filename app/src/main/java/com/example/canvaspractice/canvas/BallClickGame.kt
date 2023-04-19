package com.example.canvaspractice.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun BallClickGame() {
    var points by remember {
        mutableStateOf(0)
    }
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Points: $points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = {
                    isTimerRunning = !isTimerRunning
                    points = 0
                }
            ) {
                Text(text = if (isTimerRunning) "Reset" else "Start")
            }
            CountDownTimer(
                isTimerRunning = isTimerRunning
            ) {
                isTimerRunning = false
            }
        }

        BallClicker(
            enable = isTimerRunning
        ) {
            points++
        }
    }
}

@Composable
fun CountDownTimer(
    time: Int = 30000,
    isTimerRunning: Boolean = false,
    onTimerEnd: () -> Unit
) {
    var curTime by remember {
        mutableStateOf(time)
    }

    LaunchedEffect(key1 = curTime, key2 = isTimerRunning) {
        if (!isTimerRunning) {
            curTime = time
            return@LaunchedEffect
        }
        if (curTime > 0) {
            delay(1000L)
            curTime -= 1000
        } else {
            onTimerEnd()
        }
    }
    
    Text(
        text = (curTime / 1000).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun BallClicker(
    radius: Float = 100f,
    enable: Boolean = false,
    ballColor: Color = Color.Red,
    onBallClick: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        var ballPosition by remember {
            mutableStateOf(
                randomOffset(
                    radius = radius,
                    width = constraints.maxWidth,
                    height = constraints.maxHeight
                )
            )
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enable) {
                    if (!enable) {
                        return@pointerInput
                    }
                    detectTapGestures {
                        val distance = sqrt(
                            (it.x - ballPosition.x).pow(2) +
                                    (it.y - ballPosition.y).pow(2)
                        )
                        if (distance <= radius) {
                            ballPosition = randomOffset(
                                radius = radius,
                                width = constraints.maxWidth,
                                height = constraints.maxHeight
                            )
                            onBallClick()
                        }
                    }
                }
        ) {
            drawCircle(
                color = ballColor,
                radius = radius,
                center = ballPosition
            )
        }
    }
}

private fun randomOffset(radius: Float, width: Int, height: Int): Offset {
    return Offset(
        x = 0f,
        y = 0f
    )
}