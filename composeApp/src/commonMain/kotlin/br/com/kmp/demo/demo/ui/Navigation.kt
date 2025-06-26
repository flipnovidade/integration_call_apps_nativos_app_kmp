package br.com.kmp.demo.demo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.screen.DetailCatScreen
import br.com.kmp.demo.demo.ui.screen.ListCatsScreen
import br.com.kmp.demo.demo.ui.viewmodel.MainScreenViewModel
import kotlinx.serialization.Serializable
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = LISTCATSCREEN) {

        composable(LISTCATSCREEN) {
            ListCatsScreen(navController)
        }

        composable<CatId> { backStackEntry ->
            val catId: CatId = backStackEntry.toRoute()
            DetailCatScreen(navController = navController, idCat = catId.catId)
        }

    }
}

@Composable
fun rememberSharedViewModel(): MainScreenViewModel {
    val koin = remember { getKoin() }
    return remember { koin.get<MainScreenViewModel>() }
}

object Routes {
    const val LISTCATSCREEN = "listscreen"
}

@Serializable
data class CatId(val catId: Int)