package br.com.kmp.demo.demo.ui.components

import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import java.io.InputStream

actual fun imageBitmapFromByteArray(bytes: ByteArray, sizeImage: Dp): ImageBitmap {
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return bitmap.asImageBitmap()
}

fun getBitmapFromUri(uri: Uri, contentResolver: ContentResolver): android.graphics.Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = contentResolver.openInputStream(uri)
        val s = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        return s
    } catch (e: Exception) {
        e.printStackTrace()
        KmpLogger.e("getBitmapFromUri", "Exception: ${e.message}")
        return null
    }
}