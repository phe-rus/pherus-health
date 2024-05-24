package pherus.health.present

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pherus.health.components.MedicineReminder
import pherus.health.present.health.Appointments
import pherus.health.present.health.GeneralHealth
import pherus.health.present.health.MedicineIntake
import pherus.health.present.health.SleepTracker
import pherus.health.present.health.WaterIntake
import pherus.health.viewModel.MainViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthModule(router: NavHostController, viewmodel: MainViewModel, extra: String?) {
    val coroutine = rememberCoroutineScope()
    val navController = rememberNavController()

    var lazyState = rememberLazyListState()
    var addmedicine by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = extra.toString(),
                        fontWeight = FontWeight.Black,
                        fontSize = 23.sp,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    CloseButton(
                        coroutine = coroutine, router = router
                    )
                },
                actions = {
                    when {
                        extra.toString().contains("medicine reminder", true) -> {
                            FilledIconButton(
                                onClick = {
                                    addmedicine = true
                                }
                            ) {
                                Icon(Icons.Rounded.Add, contentDescription = null)
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        },
    ) { padv ->
        NavHost(
            navController = navController,
            startDestination = extra.toString().split(" ").first().lowercase(Locale.ROOT),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padv)
        ) {
            composable("general") {
                GeneralHealth()
            }

            composable("water") {
                WaterIntake()
            }

            composable("sleep") {
                SleepTracker()
            }

            composable("medicine") {
                MedicineIntake(
                    lazyState = lazyState
                )
            }

            composable("appointments") {
                Appointments()
            }
        }

        if (addmedicine) {
            MedicineReminder(
                onDismissRequest = {
                    addmedicine = false
                }
            )
        }
    }
}

@Composable
fun CloseButton(
    coroutine: CoroutineScope, router: NavHostController
) {
    IconButton(
        onClick = {
            coroutine.launch {
                router.popBackStack()
            }
        }, modifier = Modifier
    ) {
        Icon(
            Icons.Rounded.Close,
            contentDescription = null,
            modifier = Modifier,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}