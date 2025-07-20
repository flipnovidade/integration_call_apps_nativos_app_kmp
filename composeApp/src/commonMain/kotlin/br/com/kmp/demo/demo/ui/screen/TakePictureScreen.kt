package br.com.kmp.demo.demo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.image.SelectionMode
import br.com.kmp.demo.demo.image.rememberImagePickerLauncher
import br.com.kmp.demo.demo.ui.components.AppColors
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.TakePictureViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.profile_empty
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakePictureScreen(
    navController: NavHostController,
    takePictureViewModel: TakePictureViewModel = koinInject<TakePictureViewModel>()) {

    var newValue: String by remember { mutableStateOf("") }
    var valuePrefs: String by remember { mutableStateOf("") }
    val imageBitmap: ImageBitmap? by takePictureViewModel.myImageBitmap.collectAsState()

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                takePictureViewModel.getBitmapImage(it)
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    RegisterBackHandler  {
        navController.popBackStack()
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            TopAppBar(
                actions = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.BackHand,
                            contentDescription = "Localized description"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.CloudOff,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = { Text("List teach") }
            )

            Card(
                modifier = Modifier.fillMaxWidth().border(
                    width = 2.dp,
                    color = AppColors.blueDark,
                    shape = RoundedCornerShape(16.dp)
                ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.blueLight,
                    contentColor = AppColors.blueNormal
                ),
                elevation = CardDefaults.cardElevation()
            ) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Spacer(Modifier.height(8.dp))

                    Text(text = "Take", color = AppColors.blackNormal)
                    Text(text = "or ", color = AppColors.blackNormal)
                    Text(text = "Get ", color = AppColors.blackNormal)
                    Text(text = "PHOTO ", color = AppColors.blackNormal)

                    Spacer(Modifier.height(10.dp))


                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).size(120.dp)
                                .clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit
                        )
                    }

                    if (imageBitmap == null){
                        Image(
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).size(120.dp)
                                .clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.profile_empty)
                        )

                    }

                    Spacer(Modifier.height(15.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        onClick = {
                            if (takePictureViewModel.isPermissionGranted()){

                            }else{
                                takePictureViewModel.requestPermission()
                            }
                        }
                    ) {
                        Text(text = "TAKE", color = AppColors.whiteNormal)
                    }

                    Spacer(Modifier.height(5.dp))

                    Button(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        onClick = {
                            if (takePictureViewModel.isPermissionGranted()){
                                singleImagePicker.launch()
                            }else{
                                takePictureViewModel.requestPermission()
                            }
                        }
                    ) {
                        Text(text = "GET", color = AppColors.whiteNormal)
                    }

                }

            }

            Spacer(Modifier.height(20.dp))
        }

    }



}