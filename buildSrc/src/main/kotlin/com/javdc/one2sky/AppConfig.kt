package com.javdc.one2sky

import org.gradle.api.JavaVersion

object AppConfig {
    const val APPLICATION_ID = "com.javdc.one2sky"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "0.1.0"
    const val COMPILE_SDK_VERSION = 34
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 34

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    val JAVA_SOURCE_COMPATIBILITY = JavaVersion.VERSION_1_8
    val JAVA_TARGET_COMPATIBILITY = JavaVersion.VERSION_1_8
    const val KOTLIN_JVM_TARGET = "1.8"
    const val KOTLIN_JVM_TOOLCHAIN_JDK_VERSION = 8
}
