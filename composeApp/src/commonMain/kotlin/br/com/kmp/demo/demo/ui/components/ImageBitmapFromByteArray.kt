package br.com.kmp.demo.demo.ui.components

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

expect fun imageBitmapFromByteArray(bytes: ByteArray, sizeImage: Dp = 75.dp): ImageBitmap