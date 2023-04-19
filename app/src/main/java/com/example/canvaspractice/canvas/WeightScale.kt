package com.example.canvaspractice.canvas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WeightScale() {
    var weight by remember {
        mutableStateOf(80)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Select your weight",
            fontSize = 30.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 48.sp
                        )
                    ) {
                        append(weight.toString())
                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Green,
                            fontSize = 24.sp
                        )
                    ) {
                        append("KG")
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
            )
            Scale(
                style = ScaleStyle(
                    scaleWidth = 150.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.BottomCenter)
            ) {
                weight = it
            }
        }

        Button(
            onClick = {},
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Green
            )
        ) {
            Text(
                text = "Next",
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun WeightScalePreview() {
    WeightScale()
}