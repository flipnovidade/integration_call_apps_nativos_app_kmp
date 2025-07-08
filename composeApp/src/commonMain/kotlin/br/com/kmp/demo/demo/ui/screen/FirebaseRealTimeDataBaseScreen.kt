package br.com.kmp.demo.demo.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.kmp.demo.demo.ui.components.AppColors
import br.com.kmp.demo.demo.ui.components.RegisterBackHandler
import br.com.kmp.demo.demo.ui.viewmodel.FirebaseRealTimeDataBaseViewModel
import br.com.kmp.demo.demo.ui.viewmodel.ListItemScreenViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.getScopeName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirebaseRealTimeDataBaseScreen(
    navController: NavHostController,
    firebaseRealTimeDataBaseViewModel: FirebaseRealTimeDataBaseViewModel = koinInject<FirebaseRealTimeDataBaseViewModel>()) {

    var snapShotNodoValue: String by remember { mutableStateOf("") }
    var nodoValue: String by remember { mutableStateOf(firebaseRealTimeDataBaseViewModel.nodo.value ) }
    var keyByKeyNodo: String by remember { mutableStateOf("") }
    var valueByKeyNodo: String by remember { mutableStateOf("") }
    val stateRealTimeDataBaseViewModel: FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState by firebaseRealTimeDataBaseViewModel.stateSnapShotFiebaseDataBaseRealTimeByNodo.collectAsState()
    snapShotNodoValue = when(stateRealTimeDataBaseViewModel){
        is FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Error -> {
            (stateRealTimeDataBaseViewModel as FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Error).message
        }
        is FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Loading ->  {
            (stateRealTimeDataBaseViewModel as FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Loading).step
        }
        is FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Success ->  {
            (stateRealTimeDataBaseViewModel as FirebaseRealTimeDataBaseViewModel.GetRealTimeDataBaseUiState.Success).valueSnapShot
        }
    }

    var errorMessagePut: String by remember { mutableStateOf("") }
    val statePutRealTimeDataBaseViewModel: FirebaseRealTimeDataBaseViewModel.PutRealTimeDataBaseUiState by firebaseRealTimeDataBaseViewModel.statePutFiebaseDataBaseRealTimeByNodo.collectAsState()
    errorMessagePut = when (statePutRealTimeDataBaseViewModel){
        is FirebaseRealTimeDataBaseViewModel.PutRealTimeDataBaseUiState.Error -> {
            (statePutRealTimeDataBaseViewModel as FirebaseRealTimeDataBaseViewModel.PutRealTimeDataBaseUiState.Error) .message
        }
        FirebaseRealTimeDataBaseViewModel.PutRealTimeDataBaseUiState.Idle ->  {
            ""
        }
    }

    val coroutineScope = rememberCoroutineScope()



    DisposableEffect(Unit) {
        onDispose {
        }
    }

    RegisterBackHandler  {
        firebaseRealTimeDataBaseViewModel.clear()
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
                    Text(text = "Get all infos nodo -> $nodoValue", color = AppColors.blackNormal)
                    Text(text = "HERE -> $snapShotNodoValue", color = AppColors.blackNormal)

                    Spacer(Modifier.height(60.dp))
                    Text(text = "Add $errorMessagePut", color = AppColors.blackNormal)

                    Spacer(Modifier.height(10.dp))
                    Text(text = "Nodo", color = AppColors.blackNormal)
                    BasicTextField(
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 30.sp,
                            color = Color.DarkGray
                        ),
                        value = nodoValue,
                        onValueChange = { nodoValue = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .border(2.dp, Color.Black)
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(40.dp)
                    )

                    Spacer(Modifier.height(10.dp))
                    Text(text = "key", color = AppColors.blackNormal)
                    BasicTextField(
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 30.sp,
                            color = Color.DarkGray
                        ),
                        value = keyByKeyNodo,
                        onValueChange = { keyByKeyNodo = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .border(2.dp, Color.Black)
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(40.dp)
                    )

                    Spacer(Modifier.height(10.dp))
                    Text(text = "value", color = AppColors.blackNormal)
                    BasicTextField(
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 30.sp,
                            color = Color.DarkGray
                        ),
                        value = valueByKeyNodo,
                        onValueChange = { valueByKeyNodo = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .border(2.dp, Color.Black)
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(40.dp)
                    )

                    Spacer(Modifier.height(10.dp))
                    Button(onClick = {
                            firebaseRealTimeDataBaseViewModel.putDataFirebaseDataBaseRealTime(
                                nodoName = nodoValue,
                                keyNewNodo = keyByKeyNodo,
                                valueForNodo = valueByKeyNodo,
                            )
                    }) {
                        Text(text = "Click add value", color = AppColors.whiteNormal)
                    }

                }

            }
            Spacer(Modifier.height(20.dp))
        }

    }



}