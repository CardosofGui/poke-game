plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
    id("kotlin-android")
}

android {
    namespace = "com.example.core_android"
    compileSdk = 33

    buildFeatures {
        // Enables Jetpack Compose for this module
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.0")
    implementation("com.google.android.material:material:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    // Ktor && Serialization
    api(Ktor.ktor_serialization)
    api(Ktor.ktor_android)
    api(Ktor.kotlin_serialization)
    api(Ktor.ktor_logging)

    // Startup
    api(Startup.startup)

    // Koin
    api(Koin.koin_android)
    api(Koin.koin_insert)

    // Compose
    api(Compose.compose_ui)
    api(Compose.compose_ui_tooling_preview)
    api(Compose.compose_activity)
    api(Compose.compose_animation)
    api(Compose.compose_lifecycle)
    api(Compose.compose_material)
    api(Compose.compose_navigation)
    api(Compose.compose_ui_tooling)

    // Coil
    api(Coil.coil_compose)
    api(Coil.coil_gif)

    // FancyToast
    api(FancyToast.fancytoast)
}