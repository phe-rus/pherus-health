package pherus.health.oauth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles
import pherus.health.viewModel.MainViewModel

@Composable
fun AuthLayout(router: NavHostController, viewmodel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current
    val coroutine = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
            )
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(
                    title = "Pherus Health"
                )
                SubTitles(
                    subtitle = "Login in to Pherus Health by sliding to unlock, this will promote a Google authentication dialog to appear."
                )
                SlideToUnlock(
                    isLoading = isLoading,
                    onUnlockRequested = {
                        isLoading = true
                        coroutine.launch {
                            router.navigate("setup")
                        }
                    },
                )
            }
        }
    }
}