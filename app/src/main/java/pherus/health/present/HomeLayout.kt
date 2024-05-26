package pherus.health.present

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pherus.health.components.Bottombars
import pherus.health.components.ProfileBar
import pherus.health.components.Toolbar
import pherus.health.present.home.FeatureScreen
import pherus.health.present.home.MainScreen
import pherus.health.present.home.NotiScreen
import pherus.health.present.home.ProfileScreen
import pherus.health.viewModel.MainViewModel

@Composable
fun HomeLayout(router: NavHostController, viewmodel: MainViewModel) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val coroutine = rememberCoroutineScope()

    val navController = rememberNavController()
    val lazyScroll = rememberLazyListState()
    val scrollState = rememberScrollState()

    val profileInformtion = viewmodel.usrCollection.collectAsState().value
    var showProfileBar by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        viewmodel.initail()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Toolbar(
                basicInfor = profileInformtion?.basicInformations?.avatarHolder.toString(),
                onClick = {
                    coroutine.launch {
                        showProfileBar = true
                    }
                }
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padv)
        ) {
            val parentHeight = constraints.maxHeight
            val parentWidth = constraints.maxWidth
            NavHost(
                navController = navController,
                startDestination = selectedTab.toString(),
                modifier = Modifier.fillMaxSize()
            ) {
                composable("0") {
                    MainScreen(
                        viewmodel = viewmodel,
                        lazyScroll = lazyScroll,
                        parentHeight = parentHeight,
                        router = router,
                        coroutine = coroutine
                    )
                }
                composable("1") {
                    FeatureScreen(
                        router = router,
                        viewmodel = viewmodel,
                        lazyScroll = lazyScroll
                    )
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

        if (showProfileBar) {
            ProfileBar(
                router = router,
                coroutine = coroutine,
                profileInformtion = profileInformtion,
                onDismissRequest = {
                    showProfileBar = false
                }
            )
        }
    }
}