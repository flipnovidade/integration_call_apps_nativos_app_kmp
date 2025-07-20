package br.com.kmp.demo.demo.image

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import br.com.kmp.demo.demo.image.SelectionMode.Companion.INFINITY
import kotlinx.coroutines.CoroutineScope

@Composable
actual fun rememberImagePickerLauncher(
    selectionMode: SelectionMode,
    scope: CoroutineScope,
    resizeOptions: ResizeOptions,
    filterOptions: FilterOptions,
    onResult: (List<ByteArray>) -> Unit,
): ImagePickerLauncher {
    return when (selectionMode) {
        SelectionMode.Single ->
            pickSingleImage(
                coroutineScope = scope,
                selectionMode = selectionMode,
                resizeOptions = resizeOptions,
                filterOptions = filterOptions,
                onResult = onResult,
            )

        is SelectionMode.Multiple ->
            pickMultipleImages(
                coroutineScope = scope,
                selectionMode = selectionMode,
                resizeOptions = resizeOptions,
                filterOptions = filterOptions,
                onResult = onResult,
            )
    }
}

@Composable
private fun pickSingleImage(
    coroutineScope: CoroutineScope,
    selectionMode: SelectionMode,
    resizeOptions: ResizeOptions,
    filterOptions: FilterOptions,
    onResult: (List<ByteArray>) -> Unit,
): ImagePickerLauncher {
    val context = LocalContext.current
    var imagePickerLauncher: ImagePickerLauncher? = null
    val singleImagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                uri?.let {
                    ImageResizer.resizeImageAsync(
                        context = context,
                        coroutineScope = coroutineScope,
                        uri = uri,
                        width = resizeOptions.width,
                        height = resizeOptions.height,
                        resizeThresholdBytes = resizeOptions.resizeThresholdBytes,
                        compressionQuality = resizeOptions.compressionQuality,
                        filterOptions = filterOptions,
                    ) { resizedImage ->
                        if (resizedImage != null) {
                            onResult(listOf(resizedImage))
                        }
                    }
                }
                imagePickerLauncher?.markPhotoPickerInactive()
            },
        )

    imagePickerLauncher =
        remember {
            ImagePickerLauncher(
                selectionMode = selectionMode,
                onLaunch = {
                    singleImagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
            )
        }

    return imagePickerLauncher
}

@Composable
private fun pickMultipleImages(
    coroutineScope: CoroutineScope,
    selectionMode: SelectionMode.Multiple,
    resizeOptions: ResizeOptions,
    filterOptions: FilterOptions,
    onResult: (List<ByteArray>) -> Unit,
): ImagePickerLauncher {
    val context = LocalContext.current
    var imagePickerLauncher: ImagePickerLauncher? = null
    val maxSelection =
        if (selectionMode.maxSelection == INFINITY) {
            getMaxItems()
        } else {
            selectionMode.maxSelection
        }

    val multipleImagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(maxSelection),
            onResult = { uriList ->
                val resizedImages = mutableListOf<ByteArray>()
                uriList.forEach { uri ->
                    ImageResizer.resizeImageAsync(
                        context = context,
                        coroutineScope = coroutineScope,
                        uri = uri,
                        width = resizeOptions.width,
                        height = resizeOptions.height,
                        resizeThresholdBytes = resizeOptions.resizeThresholdBytes,
                        compressionQuality = resizeOptions.compressionQuality,
                        filterOptions = filterOptions,
                    ) { resizedImage ->
                        resizedImage?.let {
                            resizedImages.add(it)
                            if (resizedImages.size == uriList.size) {
                                onResult(resizedImages)
                            }
                        }
                    }
                }
                imagePickerLauncher?.markPhotoPickerInactive()
            },
        )

    imagePickerLauncher =
        remember {
            ImagePickerLauncher(
                selectionMode = selectionMode,
                onLaunch = {
                    multipleImagePickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
            )
        }

    return imagePickerLauncher
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ImagePickerLauncher actual constructor(
    selectionMode: SelectionMode,
    private val onLaunch: () -> Unit,
) {
    private var isPhotoPickerActive = false

    fun markPhotoPickerInactive() {
        isPhotoPickerActive = false
    }

    actual fun launch() {
        if (isPhotoPickerActive) return

        isPhotoPickerActive = true
        onLaunch()
    }
}