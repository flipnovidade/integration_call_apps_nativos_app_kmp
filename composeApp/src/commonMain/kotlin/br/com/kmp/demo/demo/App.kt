package br.com.kmp.demo.demo

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.kmp.demo.demo.ui.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {

    val navController = rememberNavController()
    AppNavigation(navController)

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

}
