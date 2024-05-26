package pherus.health

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pherus.health.config.Permissions
import pherus.health.config.Routes
import pherus.health.ui.theme.PherusTheme
import pherus.health.viewModel.MainViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            PherusTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                val navController = rememberNavController()
                val coroutine = rememberCoroutineScope()
                val fusedLocationClient =
                    remember { LocationServices.getFusedLocationProviderClient(this@MainActivity) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 0.dp
                ) {
                    LaunchedEffect(Unit) {
                        viewModel.getLocation(fusedLocationClient) { latitude, longitude ->
                            viewModel.getCountryFromLocation(
                                this@MainActivity,
                                latitude,
                                longitude
                            ) { country, locality ->
                                if (country.toString().isNotEmpty()) {
                                    coroutine.launch {
                                        viewModel.generatePatientsId(
                                            countryName = country.toString()
                                        )
                                    }
                                }
                                if (viewModel.isPreferenceRetrieve("Country").toString()
                                        .isEmpty()
                                ) {
                                    viewModel.isPreferenceStore(
                                        key = "Country",
                                        value = country!!
                                    )


                                    viewModel.isPreferenceStore(
                                        key = "LocalAddress",
                                        value = locality!!
                                    )
                                }
                            }
                        }
                    }

                    runOnUiThread {
                        fetch()
                    }

                    PermissionHandler()
                    Routes(
                        navcontroller = navController,
                        mainviewmodel = viewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        fetch()
    }

    override fun onDestroy() {
        super.onDestroy()
        fetch()
    }

    override fun onResume() {
        super.onResume()
        fetch()
    }

    private fun fetch() {
        viewModel.initail()
    }

    @SuppressLint("InlinedApi")
    @Composable
    fun PermissionHandler() {
        Permissions(
            permissionsContext = this@MainActivity,
            permissionsToRequest = arrayOf(
                android.Manifest.permission.ACCESS_WIFI_STATE,
                android.Manifest.permission.POST_NOTIFICATIONS,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            onPermissionGranted = { grantedPermissions ->
                grantedPermissions.forEach { permissionName ->
                    println("Permission granted: $permissionName")
                }
            },
            onPermissionDenied = { deniedPermissions ->
                deniedPermissions.forEach { permissionName ->
                    println("Permission denied: $permissionName")
                }
            }
        )
    }
}
