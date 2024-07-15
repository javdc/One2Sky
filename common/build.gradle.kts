import com.javdc.one2sky.AppConfig

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = AppConfig.JAVA_SOURCE_COMPATIBILITY
    targetCompatibility = AppConfig.JAVA_TARGET_COMPATIBILITY
}

kotlin {
    jvmToolchain(AppConfig.KOTLIN_JVM_TOOLCHAIN_JDK_VERSION)
}

dependencies {
    implementation(libs.kermit)
    implementation(libs.dagger.hilt.core)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.kotlinx.coroutines.core)
}
