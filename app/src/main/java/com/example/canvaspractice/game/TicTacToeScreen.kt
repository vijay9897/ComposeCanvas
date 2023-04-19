package com.example.canvaspractice.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TicTacToeScreen(
    onPlayerWin: (Player) -> Unit,
    onNewRound: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    var gameState by remember {
        mutableStateOf(emptyGameState())
    }
    var currentPlayer by remember {
        mutableStateOf<Player>(Player.X)
    }
    var isGameRunning by remember {
        mutableStateOf(true)
    }
    var rect1 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect2 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect3 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect4 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect5 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect6 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect7 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect8 by remember {
        mutableStateOf(Rect.Zero)
    }
    var rect9 by remember {
        mutableStateOf(Rect.Zero)
    }
    var animations = remember {
        emptyAnimations()
    }
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .aspectRatio(1f)
            .pointerInput(true) {
                detectTapGestures {
                    if (!isGameRunning) {
                        return@detectTapGestures
                    }
                    when {
                        rect1.contains(it) -> {
                            if (gameState[0][0] == 'E') {
                                gameState =
                                    updateGameState(gameState, 0, 0, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[0][0])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect2.contains(it) -> {
                            if (gameState[0][1] == 'E') {
                                gameState =
                                    updateGameState(gameState, 0, 1, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[0][1])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect3.contains(it) -> {
                            if (gameState[0][2] == 'E') {
                                gameState =
                                    updateGameState(gameState, 0, 2, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[0][2])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect4.contains(it) -> {
                            if (gameState[1][0] == 'E') {
                                gameState =
                                    updateGameState(gameState, 1, 0, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[1][0])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect5.contains(it) -> {
                            if (gameState[1][1] == 'E') {
                                gameState =
                                    updateGameState(gameState, 1, 1, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[1][1])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect6.contains(it) -> {
                            if (gameState[1][2] == 'E') {
                                gameState =
                                    updateGameState(gameState, 1, 2, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[1][2])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect7.contains(it) -> {
                            if (gameState[2][0] == 'E') {
                                gameState =
                                    updateGameState(gameState, 2, 0, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[2][0])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect8.contains(it) -> {
                            if (gameState[2][1] == 'E') {
                                gameState =
                                    updateGameState(gameState, 2, 1, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[2][1])
                                currentPlayer = !currentPlayer
                            }
                        }
                        rect9.contains(it) -> {
                            if (gameState[2][2] == 'E') {
                                gameState =
                                    updateGameState(gameState, 2, 2, currentPlayer.symbol)
                                scope.animateFloatToOne(animations[2][2])
                                currentPlayer = !currentPlayer
                            }
                        }
                        else -> Unit
                    }
                    val isFieldFull = gameState.all { row ->
                        row.all { it != 'E' }
                    }
                    val didXWin = didPlayerWin(gameState, Player.X)
                    val didOWin = didPlayerWin(gameState, Player.O)
                    if (didXWin) {
                        onPlayerWin(Player.X)
                    } else if(didOWin) {
                        onPlayerWin(Player.O)
                    }
                    if (isFieldFull || didXWin || didOWin) {
                        scope.launch {
                            isGameRunning = false
                            delay(5000)
                            isGameRunning = true
                            gameState = emptyGameState()
                            animations = emptyAnimations()
                            onNewRound()
                        }
                    }
                }
            }
    ) {
        val widthSegment = size.width / 3f
        val heightSegment = size.height / 3f
        rect1 = Rect(
            offset = Offset.Zero,
            size = Size(widthSegment, heightSegment)
        )
        rect2 = Rect(
            offset = Offset(widthSegment, 0f),
            size = Size(widthSegment, heightSegment)
        )
        rect3 = Rect(
            offset = Offset(widthSegment * 2, 0f),
            size = Size(widthSegment, heightSegment)
        )
        rect4 = Rect(
            offset = Offset(0f, heightSegment),
            size = Size(widthSegment, heightSegment)
        )
        rect5 = Rect(
            offset = Offset(widthSegment, heightSegment),
            size = Size(widthSegment, heightSegment)
        )
        rect6 = Rect(
            offset = Offset(widthSegment * 2, heightSegment),
            size = Size(widthSegment, heightSegment)
        )
        rect7 = Rect(
            offset = Offset(0f, heightSegment * 2),
            size = Size(widthSegment, heightSegment)
        )
        rect8 = Rect(
            offset = Offset(widthSegment, heightSegment * 2),
            size = Size(widthSegment, heightSegment)
        )
        rect9 = Rect(
            offset = Offset(widthSegment * 2, heightSegment * 2),
            size = Size(widthSegment, heightSegment)
        )

        /*vertical lines*/
        drawLine(
            color = Color.Black,
            start = Offset(
                widthSegment,
                0f
            ),
            end = Offset(
                widthSegment,
                size.height
            ),
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = Offset(
                widthSegment * 2,
                0f
            ),
            end = Offset(
                widthSegment * 2,
                size.height
            ),
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        /*horizontal lines*/
        drawLine(
            color = Color.Black,
            start = Offset(
                0f,
                heightSegment
            ),
            end = Offset(
                size.width,
                heightSegment
            ),
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = Color.Black,
            start = Offset(
                0f,
                heightSegment * 2
            ),
            end = Offset(
                size.width,
                heightSegment * 2
            ),
            strokeWidth = 10f,
            cap = StrokeCap.Round
        )
        gameState.forEachIndexed { i, row ->
            row.forEachIndexed { j, symbol ->
                if (symbol == Player.X.symbol) {
                    val path1 = Path().apply {
                        moveTo(
                            j * size.width / 3f + size.width / 6f - 50f,
                            i * size.height / 3f + size.height / 6f - 50f
                        )
                        lineTo(
                            j * size.width / 3f + size.width / 6f + 50f,
                            i * size.height / 3f + size.height / 6f + 50f
                        )
                    }
                    val path2 = Path().apply {
                        moveTo(
                            j * size.width / 3f + size.width / 6f - 50f,
                            i * size.height / 3f + size.height / 6f + 50f
                        )
                        lineTo(
                            j * size.width / 3f + size.width / 6f + 50f,
                            i * size.height / 3f + size.height / 6f - 50f,
                        )
                    }
                    val outPath1 = Path()
                    PathMeasure().apply {
                        setPath(path1, false)
                        getSegment(0f, animations[i][j].value * length, outPath1)
                    }
                    val outPath2 = Path()
                    PathMeasure().apply {
                        setPath(path2, false)
                        getSegment(0f, animations[i][j].value * length, outPath2)
                    }
                    drawPath(
                        path = outPath1,
                        color = Color.Red,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                    drawPath(
                        path = outPath2,
                        color = Color.Red,
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                } else if (symbol == Player.O.symbol){
                    drawArc(
                        color = Color.Green,
                        startAngle = 0f,
                        sweepAngle = animations[i][j].value * 360f,
                        useCenter = false,
                        topLeft = Offset(
                            x = j * widthSegment  + widthSegment / 2 - 50f,
                            y = i * heightSegment + heightSegment / 2 - 50f
                        ),
                        size = Size(100f, 100f),
                        style = Stroke(
                            width = 5.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )
                }
            }
        }
    }
}

private fun updateGameState(gameState: Array<CharArray>, i: Int, j: Int, symbol: Char): Array<CharArray> {
    val arrayCopy = gameState.copyOf()
    arrayCopy[i][j] = symbol
    return arrayCopy
}

private fun emptyAnimations(): ArrayList<ArrayList<Animatable<Float, AnimationVector1D>>> {
    val arrayList = arrayListOf<ArrayList<Animatable<Float, AnimationVector1D>>>()
    for (i in 0..2) {
        arrayList.add(arrayListOf())
        for (j in 0..2) {
            arrayList[i].add(Animatable(0f))
        }
    }
    return arrayList
}

private fun emptyGameState(): Array<CharArray> {
    return arrayOf(
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E'),
        charArrayOf('E', 'E', 'E')
    )
}

private fun CoroutineScope.animateFloatToOne(animatable: Animatable<Float, AnimationVector1D>) {
    launch {
        animatable.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 500
            )
        )
    }
}

private fun didPlayerWin(gameState: Array<CharArray>, player: Player): Boolean {
    val firstRowFull = gameState[0][0] == gameState[0][1] &&
            gameState[0][1] == gameState[0][2] &&
            gameState[0][0] == player.symbol

    val secondRowFull = gameState[1][0] == gameState[1][1] &&
            gameState[1][1] == gameState[1][2] &&
            gameState[1][0] == player.symbol

    val thirdRowFull = gameState[2][0] == gameState[2][1] &&
            gameState[2][1] == gameState[2][2] &&
            gameState[2][0] == player.symbol

    val firstColFull = gameState[0][0] == gameState[1][0] &&
            gameState[1][0] == gameState[2][0] &&
            gameState[0][0] == player.symbol

    val secondColFull = gameState[0][1] == gameState[1][1] &&
            gameState[1][1] == gameState[2][1] &&
            gameState[0][1] == player.symbol

    val thirdColFull = gameState[0][2] == gameState[1][2] &&
            gameState[1][2] == gameState[2][2] &&
            gameState[0][2] == player.symbol

    val firstDiagonalFull = gameState[0][0] == gameState[1][1] &&
            gameState[1][1] == gameState[2][2] &&
            gameState[0][0] == player.symbol

    val secondDiagonalFull = gameState[2][0] == gameState[1][1] &&
            gameState[1][1] == gameState[0][2] &&
            gameState[2][0] == player.symbol

    return firstRowFull || secondRowFull || thirdRowFull ||
            firstColFull || secondColFull || thirdColFull ||
            firstDiagonalFull || secondDiagonalFull
}

@Preview
@Composable
fun TicTacToeScreenPreview() {
    TicTacToeScreen(
        onNewRound = {},
        onPlayerWin = {}
    )
}