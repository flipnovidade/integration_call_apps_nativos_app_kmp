package br.com.kmp.demo.demo.permissionsnews

import androidx.compose.runtime.Composable

@Composable
expect fun rememberCameraManager(onResult: (SharedImage?) -> Unit): CameraManager


@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class CameraManager(
    onLaunch: () -> Unit
) {
    fun launch()
}