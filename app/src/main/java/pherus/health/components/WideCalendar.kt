package pherus.health.components

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
internal fun WideCalendar(
    closeSelection: UseCaseState.() -> Unit,
    selectedValue: (String) -> Unit
) {

    LaunchedEffect(selectedValue) {
        selectedValue("")
    }
    CalendarDialog(
        state = rememberUseCaseState(visible = true, onCloseRequest = { closeSelection() }),
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Dates { newDates ->
            if (newDates.isNotEmpty()) {
                val selectedDate = newDates.first()
                val day = selectedDate.dayOfMonth
                val month = selectedDate.monthValue
                val year = selectedDate.year
                selectedValue("$day/$month/$year")
            } else {
                selectedValue("")
            }
        },
    )
}