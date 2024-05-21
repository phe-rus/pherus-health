package pherus.health.config

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import pherus.health.oauth.AuthLayout
import pherus.health.oauth.IntroLayout
import pherus.health.present.HealthModule
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
            startDestination = if (mainviewmodel.isAuthenticated()) "companion" else "home",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("home") {
                HomeLayout(
                    router = navcontroller,
                    viewmodel = mainviewmodel
                )
            }
            composable("bio") {
                ProfileLayout(
                    router = navcontroller,
                    viewmodel = mainviewmodel
                )
            }
            composable("notify") {
                NotificationLayout(router = navcontroller)
            }
            composable("modules/{extra}") { backStackEntry ->
                HealthModule(
                    router = navcontroller,
                    viewmodel = mainviewmodel,
                    extra = backStackEntry.arguments?.getString("extra")
                )
            }
            navigation(
                startDestination = "intro",
                route = "companion"
            ) {
                composable("intro") {
                    IntroLayout(
                        router = navcontroller
                    )
                }
                composable("auth") {
                    AuthLayout(
                        router = navcontroller,
                        viewmodel = mainviewmodel
                    )
                }
            }
        }
    }
}