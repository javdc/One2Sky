package com.javdc.one2sky.presentation.util

import android.content.Context
import com.javdc.one2sky.common.util.capitalize
import com.javdc.one2sky.presentation.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dayOfWeekFormat = DateTimeFormatter.ofPattern("EEEE")

fun LocalDate.formatRelativeShortDate(context: Context): String {
    val today = LocalDate.now()
    return when {
        this == today -> context.getString(R.string.date_today)
        this == today.plusDays(1) -> context.getString(R.string.date_tomorrow)
        this == today.minusDays(1) -> context.getString(R.string.date_yesterday)
        else -> this.format(dayOfWeekFormat)
    }.capitalize()
}
