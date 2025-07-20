package br.com.kmp.demo.demo.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun ImageFromByteArray(bytes: ByteArray, sizeImage: Dp) {

    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    val imageBitmap: ImageBitmap = bitmap.asImageBitmap()

    Image(
        bitmap = imageBitmap,
        contentDescription = null,
        modifier = Modifier.size(sizeImage).clip(RoundedCornerShape(size = 12.dp)),
        alignment = Alignment.Center,
        contentScale = ContentScale.Fit,)
}