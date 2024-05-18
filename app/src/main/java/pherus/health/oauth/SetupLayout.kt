package pherus.health.oauth

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pherus.health.oauth.setup.ConsentScreen
import pherus.health.oauth.setup.HealthHistory
import pherus.health.oauth.setup.ProfileScreen
import pherus.health.viewModel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SetupLayout(router: NavHostController, viewmodel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current
    val coroutine = rememberCoroutineScope()
    val navController = rememberNavController()
    val lazyState = rememberLazyListState()

    val snackbarHostState = remember { SnackbarHostState() }
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    when (selectedTab) {
                        0 -> {
                            Text(
                                text = "General",
                                fontWeight = FontWeight.Black
                            )
                        }

                        1 -> {
                            Text(
                                text = "Medical",
                                fontWeight = FontWeight.Black
                            )
                        }

                        2 -> {
                            Text(
                                text = "End User Consent",
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                },
                actions = {
                    if (selectedTab != 0) {
                        FilledIconButton(
                            onClick = {
                                coroutine.launch {
                                    selectedTab -= 1
                                }
                            },
                            modifier = Modifier
                        ) {
                            Icon(
                                Icons.Rounded.ArrowBackIosNew,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    if (selectedTab != 2) {
                        FilledIconButton(
                            onClick = {
                                coroutine.launch {
                                    selectedTab += 1
                                }
                            },
                            modifier = Modifier
                        ) {
                            Icon(
                                Icons.AutoMirrored.Rounded.ArrowForwardIos,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                },
                windowInsets = WindowInsets.systemBars
            )
        }
    ) { pdv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = pdv),
            state = lazyState,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                NavHost(
                    navController = navController,
                    startDestination = selectedTab.toString(),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    composable("0") {
                        ProfileScreen(
                            profileAvatar = viewmodel.isProfilePictureHolder().intValue,
                            profileCollections = viewmodel.isProfileCollections()
                        )
                    }
                    composable("1") {
                        HealthHistory(
                            healthHistoryCollection = viewmodel.isHealthHistoryCollections()
                        )
                    }
                    composable("2") {
                        ConsentScreen()
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}