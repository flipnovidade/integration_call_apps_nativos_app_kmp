package br.com.kmp.demo.demo.share

import androidx.compose.runtime.Composable

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class ShareManager {
    fun shareText(text: String)
    suspend fun shareFile(file: ShareFileModel): Result<Unit>
}

@Composable
expect fun rememberShareManager(): ShareManager