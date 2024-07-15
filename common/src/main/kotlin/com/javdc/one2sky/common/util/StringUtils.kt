package com.javdc.one2sky.common.util

fun String.takeIfNotBlank() = takeIf { it.isNotBlank() }

fun String.capitalize() =
    replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
