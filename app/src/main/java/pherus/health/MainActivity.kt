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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import pherus.health.config.Permissions
import pherus.health.config.Routes
import pherus.health.ui.theme.PherusTheme
import pherus.health.viewModel.MainViewModel

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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 0.dp
                ) {
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

    private fun fetch() {

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
                android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                android.Manifest.permission.CAMERA
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
