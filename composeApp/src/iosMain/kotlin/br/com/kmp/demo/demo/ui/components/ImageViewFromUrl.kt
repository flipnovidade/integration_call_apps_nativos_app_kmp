package br.com.kmp.demo.demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.jetbrains.skia.Image as SkiaImage
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun ImageViewFromUrl(bytes: ByteArray, sizeImage: Dp) {

    val imageBitmap = SkiaImage.makeFromEncoded(bytes).toComposeImageBitmap()

    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = Modifier.size(sizeImage).clip(RoundedCornerShape(size = 12.dp)),
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,)
}