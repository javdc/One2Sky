import com.javdc.one2sky.AppConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "${AppConfig.APPLICATION_ID}.data"
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AppConfig.MIN_SDK_VERSION

        testInstrumentationRunner = AppConfig.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = AppConfig.JAVA_SOURCE_COMPATIBILITY
        targetCompatibility = AppConfig.JAVA_TARGET_COMPATIBILITY
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = AppConfig.KOTLIN_JVM_TARGET
    }

    buildFeatures {
        buildConfig = true
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }

    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.domain)

    implementation(libs.kermit)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.datastore.preferences)

    coreLibraryDesugaring(libs.desugar.jdklibs)

    testImplementation(testFixtures(projects.domain))
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
