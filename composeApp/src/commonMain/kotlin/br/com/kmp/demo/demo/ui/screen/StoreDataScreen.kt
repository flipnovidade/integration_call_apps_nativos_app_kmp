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
import br.com.kmp.demo.demo.ui.viewmodel.StoreDataViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.getScopeName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreDataScreen(
    navController: NavHostController,
    storeDataViewModel: StoreDataViewModel = koinInject<StoreDataViewModel>()) {

    var newValue: String by remember { mutableStateOf("") }
    var valuePrefs by mutableStateOf(storeDataViewModel.valueStorePrefs.collectAsState() )

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

                    Text(text = "Get value pref -> ${valuePrefs.value}", color = AppColors.blackNormal)
                    Text(text = "HERE -> ${valuePrefs.value}", color = AppColors.blackNormal)

                    Spacer(Modifier.height(10.dp))

                    BasicTextField(
                        maxLines = 1,
                        textStyle = TextStyle.Default.copy(
                            fontSize = 30.sp,
                            color = Color.DarkGray
                        ),
                        value = newValue,
                        onValueChange = { newValue = it },
                        modifier = Modifier
                            .padding(8.dp)
                            .border(2.dp, Color.Black)
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(40.dp)
                    )

                    Button(onClick = {
                        storeDataViewModel.putValueStorePrefs(valueString = newValue)
                    }) {
                        Text(text = "Click add value", color = AppColors.whiteNormal)
                    }

                }

            }
            Spacer(Modifier.height(20.dp))
        }

    }



}