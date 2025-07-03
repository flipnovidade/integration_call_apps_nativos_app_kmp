package br.com.kmp.demo.demo.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.components.AppColors
import br.com.kmp.demo.demo.ui.viewmodel.ListItemScreenViewModel
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
                .fillMaxSize(),
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
                    Text(text = "Remote config value $errorMessage",
                        color = AppColors.blackNormal)
                    Text(text = stepMessage,
                        color = AppColors.blackNormal)
                    Switch(
                        checked = valueRemoteConfigs,
                        onCheckedChange = {  },
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


        }

    }
}
