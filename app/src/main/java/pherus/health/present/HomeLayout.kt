package pherus.health.present

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pherus.health.components.Bottombars
import pherus.health.components.Toolbar
import pherus.health.present.home.FeatureScreen
import pherus.health.present.home.MainScreen
import pherus.health.present.home.NotiScreen
import pherus.health.present.home.ProfileScreen
import pherus.health.viewModel.MainViewModel

@Composable
fun HomeLayout(router: NavHostController, viewmodel: MainViewModel) {
    val coroutine = rememberCoroutineScope()
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    val navController = rememberNavController()
    val lazyScroll = rememberLazyListState()
    val scrollState = rememberScrollState()

    val profileInformtion = viewmodel.usrCollection.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Toolbar(
                router = router,
                scope = coroutine,
                basicInfor = profileInformtion?.basicInformations?.avatarHolder.toString()
            )
        },
        bottomBar = {
            Bottombars(
                onvaluechanged = {
                    selectedTab = it
                }
            )
        }
    ) { padv ->
        NavHost(
            navController = navController,
            startDestination = selectedTab.toString(),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padv)
        ) {
            composable("0") {
                MainScreen(
                    viewmodel = viewmodel
                )
            }
            composable("1") {
                FeatureScreen()
            }
            composable("2") {
                NotiScreen(
                    scrollstate = scrollState
                )
            }
            composable("3") {
                ProfileScreen(
                    scrollstate = scrollState,
                    viewmodel = viewmodel
                )
            }
        }
    }
}