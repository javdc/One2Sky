import com.javdc.one2sky.AppConfig

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = AppConfig.APPLICATION_ID
    compileSdk = AppConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AppConfig.APPLICATION_ID
        minSdk = AppConfig.MIN_SDK_VERSION
        targetSdk = AppConfig.TARGET_SDK_VERSION
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".dev"
        }
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
}

dependencies {
    implementation(projects.common)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)

    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.leakcanary.android)

    coreLibraryDesugaring(libs.desugar.jdklibs)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)
}
