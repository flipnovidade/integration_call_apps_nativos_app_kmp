package br.com.kmp.demo.demo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.ui.Routes.FIREBASEDATABASEREALTIMESSCREEN
import br.com.kmp.demo.demo.ui.Routes.PERMISSIONSLISTCONTACT
import br.com.kmp.demo.demo.ui.Routes.PREFSSTORAGE
import br.com.kmp.demo.demo.ui.Routes.TAKEORGETPICTURE
import br.com.kmp.demo.demo.ui.Routes.MAP
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.components.AppColors
import br.com.kmp.demo.demo.ui.components.imageBitmapFromByteArray
import br.com.kmp.demo.demo.ui.viewmodel.ListItemScreenViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.icon_calendar
import br.com.kmp.demo.resources.icon_location
import br.com.kmp.demo.resources.icon_prefs
import br.com.kmp.demo.resources.take_picture
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListItensScreen(
    navController: NavHostController,
    listItemsViewModel: ListItemScreenViewModel = koinInject<ListItemScreenViewModel>() ) {

    var errorMessage by remember { mutableStateOf("") }
    var stepMessage by remember { mutableStateOf("") }
    var valueRemoteConfigs by remember { mutableStateOf(false) }
    val stateRemoteConfig: ListItemScreenViewModel.RemoteConfigUiState by listItemsViewModel.stateRemoteConfig.collectAsState()
    when(stateRemoteConfig){
        is ListItemScreenViewModel.RemoteConfigUiState.Error -> {
            errorMessage = (stateRemoteConfig as ListItemScreenViewModel.RemoteConfigUiState.Error).message
        }
        is ListItemScreenViewModel.RemoteConfigUiState.Loading -> {
            stepMessage = (stateRemoteConfig as ListItemScreenViewModel.RemoteConfigUiState.Loading).step
        }
        is ListItemScreenViewModel.RemoteConfigUiState.Success -> {
            valueRemoteConfigs = (stateRemoteConfig as ListItemScreenViewModel.RemoteConfigUiState.Success).valueRemoteConfig
        }
    }

    val imageBytes by listItemsViewModel.myByteArray.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    RegisterBackHandler  {
        listItemsViewModel.clear()
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

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(weight = 1f, fill = false)
            ) {
                Spacer(Modifier.height(20.dp))

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
                        Text(
                            text = "Remote config value $errorMessage",
                            color = AppColors.blackNormal
                        )
                        Text(
                            text = stepMessage,
                            color = AppColors.blackNormal
                        )
                        Switch(
                            checked = valueRemoteConfigs,
                            onCheckedChange = { },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AppColors.blueLight,
                                uncheckedThumbColor = AppColors.blueNormal,
                                disabledCheckedThumbColor = AppColors.blueLight,
                                disabledCheckedTrackColor = AppColors.blueNormal
                            ),
                            enabled = false,
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

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
                    elevation = CardDefaults.cardElevation(),
                    onClick = { navController.navigate(FIREBASEDATABASEREALTIMESSCREEN) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Real time Data Base ", color = AppColors.blackNormal)
                        Text(text = "Nodo default message", color = AppColors.blackNormal)
                        Spacer(Modifier.height(10.dp))
                        if (imageBytes.isNotEmpty()) {
                            Image(
                                bitmap = imageBitmapFromByteArray(bytes = imageBytes),
                                contentDescription = null,
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp).size(120.dp)
                                    .clip(RoundedCornerShape(size = 12.dp)),
                                alignment = Alignment.Center,
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(modifier = Modifier.size(28.dp))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(20.dp))

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
                    elevation = CardDefaults.cardElevation(),
                    onClick = { navController.navigate(PERMISSIONSLISTCONTACT) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Permissions ", color = AppColors.blackNormal)
                        Text(text = "Show list contact", color = AppColors.blackNormal)
                        Spacer(Modifier.height(10.dp))
                        Image(
                            contentDescription = null,
                            modifier = Modifier.size(75.dp).clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.icon_calendar),
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

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
                    elevation = CardDefaults.cardElevation(),
                    onClick = { navController.navigate(PREFSSTORAGE) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Store key-values in KMP", color = AppColors.blackNormal)
                        Text(text = "in the secure way", color = AppColors.blackNormal)
                        Text(text = "iOS -> Keychain", color = AppColors.blackNormal)
                        Text(text = "Android -> EncryptedSharedPreferences", color = AppColors.blackNormal)
                        Spacer(Modifier.height(10.dp))
                        Image(
                            contentDescription = null,
                            modifier = Modifier.size(75.dp).clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.icon_prefs),
                        )
                    }

                }

                Spacer(Modifier.height(20.dp))

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
                    elevation = CardDefaults.cardElevation(),
                    onClick = { navController.navigate(TAKEORGETPICTURE) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Take Picture", color = AppColors.blackNormal)
                        Text(text = "--- ;P --", color = AppColors.blackNormal)
                        Spacer(Modifier.height(10.dp))
                        Image(
                            contentDescription = null,
                            modifier = Modifier.size(75.dp).clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.take_picture),
                        )
                    }

                }

                Spacer(Modifier.height(20.dp))

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
                    elevation = CardDefaults.cardElevation(),
                    onClick = { navController.navigate(MAP) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Location", color = AppColors.blackNormal)
                        Text(text = "--- ;P --", color = AppColors.blackNormal)
                        Spacer(Modifier.height(10.dp))
                        Image(
                            contentDescription = null,
                            modifier = Modifier.size(75.dp).clip(RoundedCornerShape(size = 12.dp)),
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Fit,
                            painter = painterResource(resource = Res.drawable.icon_location),
                        )
                    }

                }

            }
        }

    }
}
