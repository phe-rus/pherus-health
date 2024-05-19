package pherus.health.oauth

import android.os.Build
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.components.LoadingState
import pherus.health.config.Config.BasicInformations
import pherus.health.config.Config.ContactInformation
import pherus.health.config.Config.LoggedInDevice
import pherus.health.config.Config.SystemInformation
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles
import pherus.health.oauth.Authobjects.getCurrentDate
import pherus.health.viewModel.MainViewModel

@Composable
fun AuthLayout(router: NavHostController, viewmodel: MainViewModel) {
    val localFocusManager = LocalFocusManager.current
    val coroutine = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }
    var isLoading by remember { mutableStateOf(false) }
    var isLoadingbar by rememberSaveable { mutableStateOf(false) }

    val country = viewmodel.isPreferenceRetrieve("Country", "")
    val localAddress = viewmodel.isPreferenceRetrieve("LocalAddress", "")

    val googleAuthentication = viewmodel.isGoogleAuthentication(
        onAuthComplete = { result ->
            isLoadingbar = true
            isLoading = false
            coroutine.launch {
                val response = hashMapOf(
                    "basicInformations" to BasicInformations(
                        preferedName = result.user!!.displayName,
                        genderIdentity = "unknown",
                        createdAt = getCurrentDate()
                    ),
                    "contactInformation" to ContactInformation(
                        dateOfbirth = "",
                        email = result.user!!.email,
                        phoneNumber = result.user!!.phoneNumber,
                        localAddress = localAddress.toString(),
                        countryResident = country.toString()
                    ),
                    "systemInformation" to SystemInformation(
                        currentUserUid = result.user!!.uid,
                        anonymousMode = false,
                        loggedInDevices = mutableListOf(
                            LoggedInDevice(
                                deviceName = Build.BRAND,
                                deviceModel = Build.MODEL,
                                loggedInAt = getCurrentDate(),
                                loggedInFrom = Build.MANUFACTURER
                            )
                        )
                    )
                )
                viewmodel.writeToDatabase(value = response) { success, error ->
                    if (success) {
                        coroutine.launch {
                            router.navigate("home")
                            isLoadingbar = false
                        }
                    } else {
                        error?.let {
                            coroutine.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Error writing data: $it"
                                )
                                isLoadingbar = false
                            }
                        }
                    }
                }
            }
        }
    ) { _ ->
        isLoadingbar = false
    }

    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current

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
                            val gso = GoogleSignInOptions
                                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                            val googleSignInClient = GoogleSignIn.getClient(context, gso)
                            googleAuthentication.launch(googleSignInClient.signInIntent)
                        }
                    },
                )
            }
        }

        if (isLoadingbar) {
            LoadingState(
                closeSelection = {
                    isLoadingbar = false
                }
            )
        }
    }
}