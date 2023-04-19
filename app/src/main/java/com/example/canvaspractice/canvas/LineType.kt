package com.example.canvaspractice.canvas

sealed class LineType {
    object Normal: LineType()
    object FiveStep: LineType()
    object TenStep: LineType()
}
