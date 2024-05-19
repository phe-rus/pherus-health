package pherus.health.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.state.StateDialog
import com.maxkeppeler.sheets.state.models.ProgressIndicator
import com.maxkeppeler.sheets.state.models.State
import com.maxkeppeler.sheets.state.models.StateConfig
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoadingState(closeSelection: () -> Unit) {
    val progress = rememberSaveable { mutableFloatStateOf(0f) }
    val progressAnimated = animateFloatAsState(
        targetValue = progress.floatValue,
        tween(1000),
        label = "loading bar"
    ).value

    LaunchedEffect(Unit) {
        progress.floatValue = 1f
        delay(1000)
    }

    val state = State.Loading(
        "Wait a moment",
        ProgressIndicator.Linear(progressAnimated)
    )
    StateDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { closeSelection() }),
        config = StateConfig(state = state)
    )
}