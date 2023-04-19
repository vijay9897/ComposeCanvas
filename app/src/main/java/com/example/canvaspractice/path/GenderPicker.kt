package com.example.canvaspractice.path

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GenderPicker(
    modifier: Modifier = Modifier,
    maleGenderGradient: List<Color> = listOf(Color(0xFF6D6DFF), Color.Blue),
    femaleGenderGradient: List<Color> = listOf(Color(0xFFEA76FF), Color.Magenta),
    distanceBetweenGender: Dp = 16.dp,
    pathScaleFactor: Float = 7f,
    onGenderSelected: (Gender) -> Unit
) {
    var selectedGender by remember {
        mutableStateOf<Gender>(Gender.GenderFemale)
    }
    var center by remember {
        mutableStateOf(Offset.Unspecified)
    }
    val malePathString = stringResource(id = com.example.canvaspractice.R.string.man_path)
    val femalePathString = stringResource(id = com.example.canvaspractice.R.string.woman_path)

    val malePath = remember {
        PathParser().parsePathString(malePathString).toPath()
    }
    val femalePath = remember {
        PathParser().parsePathString(femalePathString).toPath()
    }

    val malePathBounds = remember {
        malePath.getBounds()
    }
    val femalePathBound = remember {
        femalePath.getBounds()
    }

    var maleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    var femaleTranslationOffset by remember {
        mutableStateOf(Offset.Zero)
    }

    var currentClickOffset by remember {
        mutableStateOf(Offset.Zero)
    }
    val maleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.GenderMale) 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    val femaleSelectionRadius = animateFloatAsState(
        targetValue = if (selectedGender is Gender.GenderFemale) 80f else 0f,
        animationSpec = tween(durationMillis = 500)
    )

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectTapGestures {
                    val transformedMaleRect = Rect(
                        offset = maleTranslationOffset,
                        size = malePathBounds.size * pathScaleFactor
                    )
                    val transformedFemaleRect = Rect(
                        offset = femaleTranslationOffset,
                        size = femalePathBound.size * pathScaleFactor
                    )
                    if (selectedGender !is Gender.GenderMale && transformedMaleRect.contains(it)) {
                        currentClickOffset = it
                        selectedGender = Gender.GenderMale
                        onGenderSelected(Gender.GenderMale)
                    } else if (selectedGender !is Gender.GenderFemale && transformedFemaleRect.contains(it)) {
                        currentClickOffset = it
                        selectedGender = Gender.GenderFemale
                        onGenderSelected(Gender.GenderFemale)
                    }
                }
            }
    ) {
        center = this.center

        maleTranslationOffset = Offset(
            x = center.x - malePathBounds.width * pathScaleFactor - distanceBetweenGender.toPx() / 2f,
            y = center.y - pathScaleFactor * malePathBounds.height / 2f
        )

        femaleTranslationOffset = Offset(
            x = center.x + distanceBetweenGender.toPx() / 2f,
            y = center.y - pathScaleFactor * femalePathBound.height / 2f
        )

        val untransformedMaleClickOffset = if (currentClickOffset == Offset.Zero) {
            malePathBounds.center
        } else {
            (currentClickOffset - maleTranslationOffset) / pathScaleFactor
        }
        val untransformedFemaleClickOffset = if (currentClickOffset == Offset.Zero) {
            femalePathBound.center
        } else {
            (currentClickOffset - femaleTranslationOffset) / pathScaleFactor
        }

        translate(
            left = maleTranslationOffset.x,
            top = maleTranslationOffset.y
        ) {
            scale(
                scale = pathScaleFactor,
                pivot = malePathBounds.topLeft
            ) {
                drawPath(
                    path = malePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = malePath
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = maleGenderGradient,
                            center = untransformedMaleClickOffset,
                            radius = maleSelectionRadius.value + 1f
                        ),
                        radius = maleSelectionRadius.value,
                        center = untransformedMaleClickOffset
                    )
                }
            }
        }

        translate(
            left = femaleTranslationOffset.x,
            top = femaleTranslationOffset.y
        ) {
            scale(
                scale = pathScaleFactor,
                pivot = femalePathBound.topLeft
            ) {
                drawPath(
                    path = femalePath,
                    color = Color.LightGray
                )
                clipPath(
                    path = femalePath
                ) {
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = femaleGenderGradient,
                            center = untransformedFemaleClickOffset,
                            radius = femaleSelectionRadius.value + 1f
                        ),
                        radius = femaleSelectionRadius.value,
                        center = untransformedFemaleClickOffset
                    )
                }
            }
        }
    }
}