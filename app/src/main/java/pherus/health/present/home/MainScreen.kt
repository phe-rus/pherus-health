package pherus.health.present.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import pherus.health.components.Content
import pherus.health.components.Header
import pherus.health.components.Pharmacies
import pherus.health.viewModel.MainViewModel

@Composable
fun MainScreen(
    viewmodel: MainViewModel,
    lazyScroll: LazyListState,
    parentHeight: Int,
    router: NavHostController,
    coroutine: CoroutineScope
) {
    val collections = viewmodel.collection.collectAsState()
    LaunchedEffect(Unit) {
        delay(100)
        viewmodel.initail()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyScroll,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Header()
        }

        item {
            Content(
                parentHeight = parentHeight,
                coroutine = coroutine,
                route = router,
                collections = collections
            )
        }

        item {
            Pharmacies()
        }

        item {

        }
    }
}