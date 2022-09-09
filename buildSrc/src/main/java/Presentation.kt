object Presentation {
    const val compose = "1.5.1"
    const val compose_ui = "1.2.1"
    const val compose_nav = "2.5.1"
    const val compose_lifecycle = "2.5.1"
    const val compose_navigation_animation = "0.26.2-beta"
    const val coil_compose = "2.0.0-rc02"
    const val coil_gif = "1.4.0"
    const val fancytoast = "2.0.1"
}

object Compose {
    const val compose_material = "androidx.compose.material:material:${Presentation.compose_ui}"
    const val compose_animation = "androidx.compose.animation:animation:${Presentation.compose_ui}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Presentation.compose_ui}"
    const val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Presentation.compose_ui}"
    const val compose_ui = "androidx.compose.ui:ui:${Presentation.compose_ui}"
    const val compose_activity = "androidx.activity:activity-compose:${Presentation.compose}"
    const val compose_lifecycle = "androidx.lifecycle:lifecycle-viewmodel-compose:${Presentation.compose_lifecycle}"
    const val compose_navigation = "androidx.navigation:navigation-compose:${Presentation.compose_nav}"

    const val compose_navigation_animation = "com.google.accompanist:accompanist-navigation-animation:${Presentation.compose_navigation_animation}"
}

object FancyToast {
    const val fancytoast = "io.github.shashank02051997:FancyToast:${Presentation.fancytoast}"
}

object Coil {
    const val coil_gif = "io.coil-kt:coil-gif:${Presentation.coil_gif}"
    const val coil_compose = "io.coil-kt:coil-compose:${Presentation.coil_compose}"
}