plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("jacoco")
}

jacoco {
    toolVersion = "0.8.8"
}

android {
    namespace = "com.example.test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            enableAndroidTestCoverage = true
            enableUnitTestCoverage = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }


}

tasks.register("jacocoTestReport", JacocoReport::class.java) {
    dependsOn("connectedDebugAndroidTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco"))
    }

    val fileTree = fileTree("${buildDir}/intermediates/javac/debug/") {
        exclude(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*"
        )
    }

    classDirectories.setFrom(fileTree)
    sourceDirectories.setFrom(files("src/main/java"))

    executionData.setFrom(fileTree("${buildDir}/outputs/code_coverage/debugAndroidTest/connected/**/*.ec"))

    doLast {
        println("Reporte HTML generado en: ${reports.html.outputLocation.get().asFile.absolutePath}")
        println("Reporte XML generado en: ${reports.xml.outputLocation.get().asFile.absolutePath}")
    }
}

tasks.register("convertEcToHtml", JacocoReport::class.java) {
    reports {
        xml.required.set(false)
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/jacoco"))
    }

    val fileTree = fileTree("${buildDir}/intermediates/javac/debug/") {
        exclude(
            "**/R.class",
            "**/R$*.class",
            "**/BuildConfig.*",
            "**/Manifest*.*",
            "**/*Test*.*",
            "android/**/*.*"
        )
    }

    classDirectories.setFrom(fileTree)
    sourceDirectories.setFrom(files("src/main/java"))

    executionData.setFrom(fileTree("${buildDir}/outputs/code_coverage/debugAndroidTest/connected/Pixel_XL_API_34(AVD) - 14/coverage.ec"))

    doLast {
        println("Reporte HTML generado en: ${reports.html.outputLocation.get().asFile.absolutePath}")
    }
}

dependencies {
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.room:room-ktx:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    ksp("com.google.devtools.ksp:symbol-processing-api:1.9.0-1.0.13")
    androidTestImplementation("androidx.room:room-testing:2.6.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("androidx.test:runner:1.5.2")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
}
