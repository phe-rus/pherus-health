package pherus.health.present

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pherus.health.components.Content
import pherus.health.components.Footer
import pherus.health.components.Header
import pherus.health.components.Modules
import pherus.health.components.Toolbar

@Composable
fun HomeLayout(router: NavHostController) {
    val coroutine = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Toolbar(
                router = router,
                scope = coroutine
            )
        }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
        ) {
            val parentHeight = maxHeight
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Header()
                }

                item {
                    Content()
                }

                item {
                    Row {
                        Text(
                            text = "User Modules",
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        )
                    }

                    Modules(parentHeight)
                }

                item {
                    Footer()
                }
            }
        }
    }
}