package br.com.kmp.demo.demo.ui.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.WidthNormal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.ui.CatId
import br.com.kmp.demo.demo.ui.components.AppColors
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import br.com.kmp.demo.demo.ui.viewmodel.PermissionsContactListViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.app_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsContactListScreen(
    navController: NavHostController,
    permissionsContactListViewModel: PermissionsContactListViewModel = koinInject<PermissionsContactListViewModel>()) {

    val pemissionContactsGranted by permissionsContactListViewModel.granted.collectAsState()
    val listContact by permissionsContactListViewModel.listContact.collectAsState()

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
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.WidthNormal,
                            contentDescription = "Localized description"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = { Text("Permissions + List contacts ${stringResource(Res.string.app_title)}") },
            )

            Card(
                modifier = Modifier.fillMaxWidth().border(
                    width = 2.dp,
                    color = AppColors.blueDark,
                    shape = RoundedCornerShape(16.dp),
                ),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.blueLight,
                    contentColor = AppColors.blueNormal
                ),
                elevation = CardDefaults.cardElevation(),
                onClick = { }
            ) {
                Spacer(Modifier.height(20.dp))
                Text( text =  "Requesting permissions contacts -> $pemissionContactsGranted",
                    Modifier.padding(18.dp).fillMaxWidth()
                )
                Spacer(Modifier.height(20.dp))
                Button(onClick = { permissionsContactListViewModel.requestPermission() }, Modifier.padding(18.dp).fillMaxWidth()
                ) {
                    Text(text = "List contacts. ", color = AppColors.whiteNormal)
                    Text(text = "Click here request permission.", color = AppColors.whiteNormal)
                }

            }

            Spacer(Modifier.height(20.dp))
            LazyColumn {
                items(listContact ) { contact ->
                    Text(text = contact, style = MaterialTheme.typography.bodyLarge)
                }
            }

        }

    }

}