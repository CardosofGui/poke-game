package com.core.presentation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationScreenTransition(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally(),
        content = content,
        initiallyVisible = false
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimationInitialScreenTransition(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(),
        exit = slideOutVertically(),
        content = content,
        initiallyVisible = false
    )
}