package br.com.kmp.demo.demo.ui.screen

import AlertDialogError
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardReturn
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCatScreen(
    navController: NavHostController,
    idCat: Int) {

    val scopeId = rememberSaveable { LISTCATSCREEN }
    val scope = remember(scopeId) {
        getKoin().getScope(scopeId)
    }
    val viewModel = remember { scope.get<MainScreenViewModel>() }
    val state by viewModel.stateDetailCat.collectAsState()
    viewModel.getCatById()

    RegisterBackHandler  {
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
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardReturn,
                            contentDescription = "Backpressed"
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.CloudOff,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = { Text("Detail Cats") }
            )

            when(state){
                is MainScreenViewModel.DetailCatUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MainScreenViewModel.DetailCatUiState.Error -> {

                    val errorText = (state as MainScreenViewModel.DetailCatUiState.Error).message

                    AlertDialogError(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = "Icon of floating action button",
                                modifier = Modifier.size(45.dp)
                            )
                        },
                        dialogTitle = "Atenção",
                        dialogText = errorText,

                        showBtnTextConfirm = true,
                        btnTextConfirm = "Tentar Novamente",
                        onConfirmation = {
                            viewModel.getCatById()
                        },

                        showBtnTextDismiss = true,
                        btnTextDismiss = "Voltar",
                        onDismissRequest = {
                            navController.popBackStack()
                        },

                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                    )
                }
                is MainScreenViewModel.DetailCatUiState.SuccessGetCat -> {

                    val cat = (state as MainScreenViewModel.DetailCatUiState.SuccessGetCat).cat

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Text(text = cat.name, style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = cat.data.toString(), style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}
