package com.javdc.one2sky.common.util

import java.time.format.DateTimeFormatter
import java.util.Locale

val serverDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)
val serverDateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a", Locale.US)
