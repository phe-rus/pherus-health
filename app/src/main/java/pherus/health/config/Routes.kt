package pherus.health.config

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pherus.health.present.HomeLayout
import pherus.health.present.NotificationLayout
import pherus.health.present.ProfileLayout
import pherus.health.viewModel.MainViewModel

@Composable
fun Routes(
    navcontroller: NavHostController,
    mainviewmodel: MainViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navcontroller,
            startDestination = "home",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("home") {
                HomeLayout(router = navcontroller)
            }

            composable("bio") {
                ProfileLayout(router = navcontroller)
            }
            composable("notify") {
                NotificationLayout(router = navcontroller)
            }
        }
    }
}