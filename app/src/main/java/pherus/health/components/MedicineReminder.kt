package pherus.health.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.config.Config.Recurrence.Daily
import pherus.health.config.Config.getRecurrenceList
import pherus.health.config.Config.returnMedicineForm
import pherus.health.config.Config.returnMedicineTime
import pherus.health.config.Config.toFormattedString
import java.util.Date

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MedicineReminder(
    onDismissRequest: () -> Unit
) {
    var medicationName by rememberSaveable { mutableStateOf("") }
    var personalNotes by rememberSaveable { mutableStateOf("") }
    var numberOfDosage by rememberSaveable { mutableStateOf("1") }
    var recurrence by rememberSaveable { mutableStateOf(Daily.name) }
    var isMaxDoseError by rememberSaveable { mutableStateOf(false) }
    var endDate by rememberSaveable { mutableStateOf("") }
    val maxDose = 6

    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp
    ) {
        FlowColumn(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = scrollState, true)
                .padding(10.dp), verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Set up medicine\nReminder",
                    fontWeight = FontWeight.Black,
                    fontSize = 32.sp,
                    lineHeight = 32.sp
                )

                IconButton(
                    onClick = {
                        coroutine.launch {
                            // router.popBackStack()
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

            OutlinedTextField(
                value = medicationName,
                onValueChange = { medName ->
                    medicationName = medName
                },
                placeholder = {
                    Text(
                        text = "Medication Name",
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    MedicineDosage(
                        modifier = Modifier.weight(0.3F),
                        numberOfDosageUnit = { nod -> numberOfDosage = nod },
                        isMaxDoseErrorUnit = { imd -> isMaxDoseError = imd },
                        maxDose = maxDose
                    )

                    RecurrenceDropdownMenu(
                        modifier = Modifier.weight(0.7F),
                        recurrence = {
                            recurrence = it
                        }
                    )
                }

                if (isMaxDoseError) {
                    Text(
                        text = "You cannot have more than 99 dosage per day.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            EndDateTextField { endDate = it }

            Text(
                text = "Time Of Day",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                lineHeight = 18.sp
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                maxItemsInEachRow = 5
            ) {
                returnMedicineTime().fastForEachIndexed { _, timeOD ->
                    ElevatedFilterChip(
                        selected = false,
                        onClick = {
                                  
                        },
                        label = {
                            Text(
                                text = timeOD,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                        },
                        shape = RoundedCornerShape(100)
                    )
                }
            }

            Text(
                text = "Medicine Form",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                lineHeight = 18.sp
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                maxItemsInEachRow = 5
            ) {
                returnMedicineForm().forEachIndexed { _, form ->
                    ElevatedFilterChip(
                        selected = false,
                        onClick = { /*TODO*/ },
                        label = {
                            Text(
                                text = form,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                lineHeight = 12.sp
                            )
                        },
                        shape = RoundedCornerShape(100)
                    )
                }
            }

            OutlinedTextField(
                value = personalNotes,
                onValueChange = { pnotes ->
                    personalNotes = pnotes
                },
                placeholder = {
                    Text(
                        text = "Personal Notes",
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp, max = 200.dp)
            )

            OutlinedButton(
                onClick = {},
                shape = RoundedCornerShape(10),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
        }
    }
}

@Composable
fun MedicineDosage(
    modifier: Modifier,
    numberOfDosageUnit: (String) -> Unit,
    isMaxDoseErrorUnit: (Boolean) -> Unit,
    maxDose: Int
) {
    var isMaxDoseError by rememberSaveable { mutableStateOf(false) }
    var numberOfDosage by rememberSaveable { mutableStateOf("1") }

    LaunchedEffect(Unit) {
        isMaxDoseErrorUnit(isMaxDoseError)
        numberOfDosageUnit(numberOfDosage)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "Dosages",
            style = MaterialTheme.typography.bodyLarge
        )

        OutlinedTextField(
            value = numberOfDosage,
            onValueChange = { dosa ->
                if (dosa.length < maxDose) {
                    isMaxDoseError = false
                    numberOfDosage = dosa
                } else {
                    isMaxDoseError = true
                }
            },
            placeholder = {
                Text(
                    text = "eg 1",
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            trailingIcon = {
                if (isMaxDoseError) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            },
            textStyle = TextStyle(
                fontSize = 12.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Bold
            ),
            isError = isMaxDoseError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.height(45.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecurrenceDropdownMenu(
    modifier: Modifier,
    recurrence: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recurrence),
            style = MaterialTheme.typography.bodyLarge
        )

        val options = getRecurrenceList().map { it.name }
        var expanded by rememberSaveable { mutableStateOf(false) }
        var selectedOptionText by rememberSaveable { mutableStateOf(options[0]) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .menuAnchor()
                    .height(45.dp),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                textStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            recurrence(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun EndDateTextField(endDate: (String) -> Unit) {
    Text(
        text = stringResource(id = R.string.end_date),
        style = MaterialTheme.typography.bodyLarge
    )

    val showCalander = rememberSaveable { mutableStateOf(false) }

    val currentDate = Date().toFormattedString()
    var selectedDate by rememberSaveable { mutableStateOf(currentDate) }

    LaunchedEffect(selectedDate) {
        endDate(selectedDate)
    }
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        readOnly = true,
        value = selectedDate,
        onValueChange = {},
        textStyle = TextStyle(
            fontSize = 12.sp,
            lineHeight = 12.sp,
            fontWeight = FontWeight.Bold
        ),
        trailingIcon = { Icons.Rounded.DateRange },
        interactionSource = remember { MutableInteractionSource() }
            .also { interactionSource ->
                LaunchedEffect(interactionSource) {
                    interactionSource.interactions.collect {
                        if (it is PressInteraction.Release) {
                            showCalander.value = true
                        }
                    }
                }
            }
    )

    if (showCalander.value) {
        WideCalendar(
            closeSelection = {
                showCalander.value = false
            },
            selectedValue = {
                selectedDate = it.replace("/", ",")
            }
        )
    }
}

private fun handleSelection(
    isSelected: Boolean,
    selectionCount: Int,
    canSelectMoreTimesOfDay: Boolean,
    onStateChange: (Int, Boolean) -> Unit,
    onShowMaxSelectionError: () -> Unit
) {
    if (isSelected) {
        onStateChange(selectionCount - 1, !isSelected)
    } else {
        if (canSelectMoreTimesOfDay) {
            onStateChange(selectionCount + 1, !isSelected)
        } else {
            onShowMaxSelectionError()
        }
    }
}

private fun canSelectMoreTimesOfDay(selectionCount: Int, numberOfDosage: Int): Boolean {
    return selectionCount < numberOfDosage
}