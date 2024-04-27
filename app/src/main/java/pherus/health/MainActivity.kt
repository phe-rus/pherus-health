package pherus.health

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import pherus.health.config.Routes
import pherus.health.ui.theme.PherusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PherusTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    tonalElevation = 8.dp
                ) {
                    runOnUiThread {

                    }
                    Routes(navcontroller = navController)
                }
            }
        }
    }
}
