object Versions {
    const val koin = "3.2.0"
    const val ktor = "1.5.0"
    const val kotlin_serialization = "1.0.1"
    const val compose = "1.5.1"
    const val compose_ui = "1.2.1"
    const val compose_nav = "2.5.1"
    const val compose_lifecycle = "2.5.1"
    const val coil_compose = "2.0.0-rc02"
    const val coil_gif = "1.4.0"
    const val fancytoast = "2.0.1"
    const val startup = "1.1.1"
}

object Koin {
    const val koin_insert = "io.insert-koin:koin-core:${Versions.koin}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koin}"
}

object Ktor {
    const val ktor_serialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
    const val ktor_android = "io.ktor:ktor-client-android:${Versions.ktor}"
    const val ktor_logging = "io.ktor:ktor-client-logging-jvm:${Versions.ktor}"
    const val kotlin_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlin_serialization}"
}

object Startup {
    const val startup = "androidx.startup:startup-runtime:${Versions.startup}"
}

object Compose {
    const val compose_material = "androidx.compose.material:material:${Versions.compose_ui}"
    const val compose_animation = "androidx.compose.animation:animation:${Versions.compose_ui}"
    const val compose_ui = "androidx.compose.ui:ui-tooling:${Versions.compose_ui}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.compose}"
    const val compose_lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.compose_lifecycle}"
    const val compose_navigation = "androidx.navigation:navigation-compose:${Versions.compose_nav}"
}

object FancyToast {
    const val fancytoast = "io.github.shashank02051997:FancyToast:${Versions.fancytoast}"
}

object Coil {
    const val coil_gif = "io.coil-kt:coil-gif:${Versions.coil_gif}"
    const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil_compose}"
}