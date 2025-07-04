package br.com.kmp.demo.demo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import br.com.kmp.demo.demo.ui.Routes.LISTCATSCREEN
import br.com.kmp.demo.demo.ui.Routes.LISTITEMSCREEN
import br.com.kmp.demo.demo.ui.screen.DetailCatScreen
import br.com.kmp.demo.demo.ui.screen.ListCatsScreen
import br.com.kmp.demo.demo.ui.screen.ListItensScreen
import kotlinx.serialization.Serializable

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

        composable(LISTITEMSCREEN) {
            ListItensScreen(navController)
        }

    }
}

object Routes {
    const val LISTCATSCREEN = "listscreen"
    const val LISTITEMSCREEN = "listitensscreen"
}

@Serializable
data class CatId(val catId: Int)
