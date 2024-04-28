package pherus.health.config

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.core.content.ContextCompat

@SuppressLint("InlinedApi")
@Composable
fun Permissions(
    permissionsContext: Context,
    permissionsToRequest: Array<String>,
    onPermissionDenied: (List<String>) -> Unit = {},
    onPermissionGranted: (List<String>) -> Unit = {},
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val grantedPermissions = permissions.filterValues { it }.keys.toList()
        val deniedPermissions = permissions.filterValues { !it }.keys.toList()

        if (deniedPermissions.isNotEmpty()) {
            onPermissionDenied(deniedPermissions)
        } else {
            onPermissionGranted(grantedPermissions)
        }
    }

    DisposableEffect(Unit) {
        val isPermissionGranted = permissionsToRequest.all { permission ->
            ContextCompat.checkSelfPermission(
                permissionsContext,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (!isPermissionGranted) {
            launcher.launch(permissionsToRequest)
        } else {
            onPermissionGranted(permissionsToRequest.toList()) // Handle initial granted state
        }

        onDispose { }
    }
}