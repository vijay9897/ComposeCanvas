package com.example.canvaspractice.canvas

sealed interface IndicatorType {
    object MinuteIndicator: IndicatorType
    object HourIndicator: IndicatorType
}
