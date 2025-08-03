package br.com.kmp.demo.demo.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.location.model.Permission
import br.com.kmp.demo.demo.location.model.PermissionState
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.LocationScreenViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
            navController: NavHostController,
            locationScreenViewModel: LocationScreenViewModel = koinInject<LocationScreenViewModel>()){

    val locationState by locationScreenViewModel.stateLocation.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    RegisterBackHandler  {
        navController.popBackStack()
    }

    val scope = rememberCoroutineScope()

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
                            contentDescription = "Locations"
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
                title = { Text("List providers locations") }
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {



                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(
                        top = 64.dp,
                        bottom = 64.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Column {

                            Text("Localização atual:", style = MaterialTheme.typography.titleLarge)

                            locationState?.let { loc ->
                                Text("Latitude: ${loc.latitude}")
                                Text("Longitude: ${loc.longitude}")
                            } ?: Text("Aguardando localização...")

                            Spacer(Modifier.height(16.dp))

                            Text(
                                text = "Permissions",
                                color = Color.Black,
                            )
                            Divider()
                        }
                    }
                    items(Permission.entries.toTypedArray()) { permission ->
                        val permissionState by locationScreenViewModel.permissionsService.checkPermissionFlow(permission)
                            .collectAsState(locationScreenViewModel.permissionsService.checkPermission(permission))

                        PermissionItem(
                            permissionName = permission.name,
                            permissionState = permissionState,
                            onRequestClick = {
                                scope.launch {
                                    locationScreenViewModel.permissionsService.providePermission(permission)
                                }
                            },
                            onOpenSettingsClick = {
                                locationScreenViewModel.permissionsService.openSettingPage(permission)
                            },
                        )

                    }
                }
            }

        }
    }

}


@Suppress("FunctionName")
@Composable
internal fun PermissionItem(
    permissionName: String,
    permissionState: PermissionState,
    onRequestClick: () -> Unit,
    onOpenSettingsClick: () -> Unit,
) {
    val colorState by animateColorAsState(
        when (permissionState) {
            PermissionState.GRANTED -> Color.Green
            PermissionState.NOT_DETERMINED -> Color.Gray
            PermissionState.DENIED -> Color.Red
        }
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = permissionName,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = when (permissionState) {
                    PermissionState.GRANTED -> Icons.Default.Check
                    PermissionState.NOT_DETERMINED -> Icons.Outlined.QuestionMark
                    PermissionState.DENIED -> Icons.Outlined.Close
                },
                tint = colorState,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Button(
                onClick = onOpenSettingsClick,
            ) {
                Text(
                    text = "Settings",
                    color = Color.White,
                )
            }
        }
        AnimatedVisibility(permissionState.notGranted()) {
            Button(
                onClick = onRequestClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Request",
                    color = Color.White,
                )
            }
        }
    }
}