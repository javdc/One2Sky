package com.javdc.one2sky.common.util

fun <T> Iterable<T>.areAllTheSame(): Boolean {
    return this.distinct().size == 1
}
