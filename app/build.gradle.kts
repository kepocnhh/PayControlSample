import com.android.build.api.variant.ApplicationVariant
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    google()
    mavenCentral()
    maven("https://repo.paycontrol.org/android/maven")
}

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "test.android.paycontrol"
    compileSdk = Version.Android.compileSdk

    defaultConfig {
        applicationId = namespace
        minSdk = Version.Android.minSdk
        targetSdk = Version.Android.targetSdk
        versionCode = 1
        versionName = "0.0.$versionCode"
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            isMinifyEnabled = false
            isShrinkResources = false
            manifestPlaceholders["buildType"] = name
        }
    }

    buildFeatures.buildConfig = true
}

fun afterEvaluate(variant: ApplicationVariant) {
    val supported = setOf("debug")
    if (!supported.contains(variant.name)) {
        tasks.getByName("pre${variant.name.replaceFirstChar(Char::titlecase)}Build") {
            doFirst {
                error("Variant \"${variant.name}\" is not supported!")
            }
        }
        return
    }
    tasks.getByName<JavaCompile>("compile${variant.name.replaceFirstChar(Char::titlecase)}JavaWithJavac") {
        targetCompatibility = Version.jvmTarget
    }
    tasks.getByName<KotlinCompile>("compile${variant.name.replaceFirstChar(Char::titlecase)}Kotlin") {
        kotlinOptions.jvmTarget = Version.jvmTarget
    }
}

androidComponents.onVariants { variant ->
    val output = variant.outputs.single()
    check(output is com.android.build.api.variant.impl.VariantOutputImpl)
    output.outputFileName = listOf(
        rootProject.name,
        android.defaultConfig.versionName!!,
        variant.name,
        android.defaultConfig.versionCode!!.toString(),
    ).joinToString(separator = "-", postfix = ".apk")
    afterEvaluate {
        afterEvaluate(variant)
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("tech.paycon.sdk.v5:pcsdk:6.0.407")
}
