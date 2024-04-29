package pherus.health.oauth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun AuthLayout(router: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.fillMaxSize()
        ) {
            item {

            }
        }
    }
}