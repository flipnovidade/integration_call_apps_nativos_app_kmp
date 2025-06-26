package br.com.kmp.demo.demo.ui.screen

import AlertDialogError
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.WidthNormal
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.Platform
import br.com.kmp.demo.demo.getPlatform
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.ui.CatId
import br.com.kmp.demo.demo.ui.Routes
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListCatsScreen(navController: NavHostController) {

    val scopeId = rememberSaveable { LISTCATSCREEN }
    val scope = remember(scopeId) {
        getKoin().getOrCreateScope(scopeId, named(LISTCATSCREEN))
    }
    val viewModel = remember { scope.get<MainScreenViewModel>() }

    val state by viewModel.state.collectAsState()
    viewModel.getCats()

    DisposableEffect(Unit) {
        onDispose {
        }
    }

    RegisterBackHandler  {
        viewModel.clear()
        getKoin().getScope(LISTCATSCREEN).close()
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
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.WidthNormal,
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
                title = { Text("Felippe Cats") }
            )

            when(state){
                is MainScreenViewModel.MainUiState.Error -> {

                    val errorText = (state as MainScreenViewModel.MainUiState.Error).message

                    AlertDialogError(
                        icon = {
                            Icon(
                                painter= painterResource(Res.drawable.compose_multiplatform),
                                contentDescription = "Icon of floating action button",
                                modifier = Modifier.size(45.dp)
                            )
                        },
                        onDismissRequest = { },
                        dialogTitle = "Atenção",
                        dialogText = errorText,

                        showBtnTextConfirm = true,
                        btnTextConfirm = "Tentar Novamente",
                        onConfirmation = {
                            viewModel.getCats()
                        },

                        showBtnTextDismiss = false,

                        dismissOnBackPress = false,
                        dismissOnClickOutside = false,
                    )
                }
                is MainScreenViewModel.MainUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MainScreenViewModel.MainUiState.Success -> {

                    val cats = (state as MainScreenViewModel.MainUiState.Success).cats
                    LazyColumn {
                        items(cats ) { cat ->
                            CatListItem(
                                cat = cat,
                                onClick = {
                                    viewModel.setCatClicked(cat)
                                    navController.navigate(route = CatId(cat.id.toInt()) ) {
                                        popUpTo<CatId>(){
                                            inclusive = true
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                    }
                                },
                            )
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun CatListItem(cat: Cat, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = cat.name, style = MaterialTheme.typography.bodyLarge)
    }
}