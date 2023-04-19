package com.example.canvaspractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.canvaspractice.canvas.ClockScreen
import com.example.canvaspractice.canvas.ClockStyle
import com.example.canvaspractice.game.Player
import com.example.canvaspractice.game.TicTacToeScreen
import com.example.canvaspractice.path.GenderPicker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var winningPlayer by remember {
                mutableStateOf<Player?>(null)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TicTacToeScreen(
                    onNewRound = {
                        winningPlayer = null
                    },
                    onPlayerWin = {
                        winningPlayer = it
                    }
                )
                Spacer(modifier = Modifier.height(50.dp))

                winningPlayer?.let {
                    Text(
                        text = "Player ${it.symbol} has won!",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

