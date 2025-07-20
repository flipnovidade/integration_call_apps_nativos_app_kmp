package br.com.kmp.demo.demo.ui.components

import org.jetbrains.skia.Image as SkiaImage
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.Dp

actual fun imageBitmapFromByteArray(bytes: ByteArray, sizeImage: Dp): ImageBitmap {
    return SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()
}