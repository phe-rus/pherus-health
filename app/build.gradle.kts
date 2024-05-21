plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android") apply false
}

android {
    namespace = "pherus.health"
    compileSdk = 34

    android.buildFeatures.buildConfig = true
    defaultConfig {
        applicationId = "pherus.health"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
        compose = true
        dataBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.aar", "*.jar"))))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // ui libraries
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.material)
    implementation(libs.androidx.material.icons.extended.android)

    // compose navigation
    implementation(libs.androidx.navigation.compose)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // Multidex
    implementation(libs.gson)
    implementation(libs.mmkv.static)
    implementation(libs.androidx.multidex)
    implementation(libs.accompanist.permissions)

    // Coil Image
    implementation(libs.coil.compose)
    implementation(libs.haze)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.database)
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.kotlinx.coroutines.play.services)

    // Sheets Compose
    implementation(libs.sheets.compose.core)
    implementation(libs.sheets.compose.calendar)
    implementation(libs.sheets.compose.state)
    implementation(libs.sheets.compose.rating)
    implementation(libs.sheets.compose.option)
    implementation(libs.sheets.compose.list)
    implementation(libs.sheets.compose.input)
    implementation(libs.sheets.compose.info)
    implementation(libs.sheets.compose.duration)
    implementation(libs.sheets.compose.date.time)
    implementation(libs.sheets.compose.color)
    implementation(libs.sheets.compose.clock)

    // viewmodel
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    //Serialization
    implementation(kotlin("stdlib"))
    implementation(libs.kotlinx.serialization.json)

    // Worker manager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.work.multiprocess)

    // Material3 Android
    implementation(libs.androidx.preference.ktx)
    implementation(libs.google.material)
    implementation(libs.play.services.location)

    // android libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}