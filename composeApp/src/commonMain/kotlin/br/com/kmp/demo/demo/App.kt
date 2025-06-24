package br.com.kmp.demo.demo

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.kmp.demo.demo.model.Cat
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import br.com.kmp.demo.resources.Res
import br.com.kmp.demo.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    startKoin()

    val viewModel: MainScreenViewModel = getKoin().get()
    val state by viewModel.state.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.clear()
        }
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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = { Text("Cats") }
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
                MainScreenViewModel.MainUiState.Loading -> {
                    CircularProgressIndicator()
                }
                is MainScreenViewModel.MainUiState.Selected -> {
                    //Go to the Next Screen
                }
                is MainScreenViewModel.MainUiState.Success -> {

                    val cats = (state as MainScreenViewModel.MainUiState.Success).cats
                    LazyColumn {
                        items(cats ) { cat ->
                            CatListItem(
                                cat = cat,
                                onClick = { viewModel.selectCat(cat) },
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