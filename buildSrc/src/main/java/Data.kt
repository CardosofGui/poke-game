object Data {
    const val koin = "3.2.0"
    const val ktor = "1.5.0"
    const val kotlin_serialization = "1.0.1"
}

object Koin {
    const val koin_insert = "io.insert-koin:koin-core:${Data.koin}"
    const val koin_android = "io.insert-koin:koin-android:${Data.koin}"
}

object Ktor {
    const val ktor_serialization = "io.ktor:ktor-client-serialization:${Data.ktor}"
    const val ktor_android = "io.ktor:ktor-client-android:${Data.ktor}"
    const val ktor_logging = "io.ktor:ktor-client-logging-jvm:${Data.ktor}"
    const val kotlin_serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Data.kotlin_serialization}"
}
