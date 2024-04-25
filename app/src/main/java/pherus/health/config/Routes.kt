package pherus.health.config

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pherus.health.present.HomeLayout

@Composable
fun Routes(
    navcontroller: NavHostController
) {
    NavHost(
        navController = navcontroller,
        startDestination = "home",
        modifier = Modifier.fillMaxSize()
    ) {
        composable("home") {
            HomeLayout()
        }
    }
}