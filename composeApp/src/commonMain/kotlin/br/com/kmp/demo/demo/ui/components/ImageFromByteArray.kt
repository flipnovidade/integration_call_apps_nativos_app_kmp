package br.com.kmp.demo.demo.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun ImageFromByteArray(bytes: ByteArray, sizeImage: Dp = 75.dp)