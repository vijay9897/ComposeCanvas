package com.example.canvaspractice.canvas

import android.graphics.Color
import androidx.compose.ui.graphics.Color as ComposeColor
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.time.Duration.Companion.seconds

@Composable
fun ClockScreen(
    style: ClockStyle,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = ComposeColor.White
            ),
        contentAlignment = Alignment.Center
    ) {
        val milliSeconds = remember {
            System.currentTimeMillis() + 19800000
        }
        var seconds by remember {
            mutableStateOf((milliSeconds / 1000f) % 60f)
        }
        var minute by remember {
            mutableStateOf(((milliSeconds / 1000f) / 60f) % 60f)
        }
        var hour by remember {
            mutableStateOf(((milliSeconds / 1000f) / 3600f) % 12f)
        }
        LaunchedEffect(key1 = seconds) {
            delay(1.seconds)
            minute += 1f / 60f
            hour += 1f / (60f * 12f)
            seconds = (seconds + 1f) % 60
        }

        Canvas(
            modifier = Modifier
                .size(300.dp)
        ) {
            val radius = size.width / 2
            drawContext.canvas.nativeCanvas.apply {
                drawCircle(
                    center.x,
                    center.y,
                    radius,
                    Paint().apply {
                        color = Color.WHITE
                        setShadowLayer(
                            50f,
                            0f,
                            0f,
                            Color.argb(50, 0, 0, 0)
                        )
                    }
                )
                drawCircle(
                    center.x,
                    center.y,
                    radius * 0.1f,
                    Paint().apply {
                        color = Color.BLACK
                        setShadowLayer(
                            20f,
                            0f,
                            0f,
                            Color.argb(50, 0, 0, 0)
                        )
                    }
                )
            }
            /*drawing minute and hour indicator lines*/
            (0..59).forEach { number ->
                val lineType = when {
                    number % 5 == 0 -> IndicatorType.HourIndicator
                    else -> IndicatorType.MinuteIndicator
                }
                val stokeWidth: Dp
                val lineColor: ComposeColor
                val lineHeight: Dp
                when (lineType) {
                    IndicatorType.HourIndicator -> {
                        lineColor = style.hourIndicatorColor
                        lineHeight = style.hourIndicatorHeight
                        stokeWidth = 2.dp
                    }
                    IndicatorType.MinuteIndicator -> {
                        lineColor = style.minuteIndicatorColor
                        lineHeight = style.minuteIndicatorHeight
                        stokeWidth = 1.dp
                    }
                }
                val angleInRad = (number * 360f / 60f) * (PI / 180).toFloat()
                val lineStart = Offset(
                    x = (radius - lineHeight.toPx()) * cos(angleInRad) + center.x,
                    y = (radius - lineHeight.toPx()) * sin(angleInRad) + center.y
                )
                val lineEnd = Offset(
                    x = radius * cos(angleInRad) + center.x,
                    y = radius * sin(angleInRad) + center.y
                )
                drawLine(
                    color = lineColor,
                    start = lineStart,
                    end = lineEnd,
                    strokeWidth = stokeWidth.toPx()
                )
                if (number % 5 == 0 && number % 15 == 0) {
                    drawContext.canvas.nativeCanvas.apply {
                        val displayNumber = number / 15 * 3 + 3
                        var x = 0f
                        var y = 0f
                        when (displayNumber) {
                            3 -> {
                                x = (radius - lineHeight.toPx()) * cos(angleInRad) + center.x - 20
                                y = (radius - lineHeight.toPx()) * sin(angleInRad) + center.y + 10
                            }
                            6 -> {
                                x = (radius - lineHeight.toPx()) * cos(angleInRad) + center.x
                                y = (radius - lineHeight.toPx()) * sin(angleInRad) + center.y - 10
                            }
                            9 -> {
                                x = (radius - lineHeight.toPx()) * cos(angleInRad) + center.x + 20
                                y = (radius - lineHeight.toPx()) * sin(angleInRad) + center.y + 10
                            }
                            12 -> {
                                x = (radius - lineHeight.toPx()) * cos(angleInRad) + center.x
                                y = (radius - lineHeight.toPx()) * sin(angleInRad) + center.y + 30
                            }
                        }
                        drawText(
                            "$displayNumber",
                            x,
                            y,
                            Paint().apply {
                                color = Color.RED
                                textSize = 40f
                                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                                textAlign = Paint.Align.CENTER

                            }
                        )
                    }
                }
            }
            /*drawing hour hand*/
            rotate(
                degrees = hour * (360 / 12f)
            ) {
                drawLine(
                    color = ComposeColor.Black,
                    start = center,
                    end = Offset(center.x, radius * 0.3f),
                    strokeWidth = 15f,
                    cap = StrokeCap.Round
                )
            }
            /*drawing minute hand*/
            rotate(
                degrees = minute * (360 / 60f)
            ) {
                drawLine(
                    color = ComposeColor.Black,
                    start = center,
                    end = Offset(center.x, radius * 0.1f),
                    strokeWidth = 10f,
                    cap = StrokeCap.Round
                )
            }
            /*drawing seconds hands*/
            rotate(
                degrees = seconds * (360 / 60f)
            ) {
                drawLine(
                    color = ComposeColor.Red,
                    start = center,
                    end = Offset(center.x, radius * 0.15f),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Preview
@Composable
fun ClockScreenPreview() {
    ClockScreen(style = ClockStyle())
}