package br.com.kmp.demo.demo.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun RegisterBackHandler(onBack: () -> Unit) {
    BackHandler {
        onBack()
    }
}