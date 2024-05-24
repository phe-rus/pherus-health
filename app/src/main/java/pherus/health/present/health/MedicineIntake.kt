package pherus.health.present.health

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import pherus.health.R
import pherus.health.config.Config.MedicineForm
import pherus.health.config.Config.MedicineProps
import pherus.health.config.Config.Recurrence.Daily
import pherus.health.config.Config.Recurrence.Weekly
import pherus.health.config.Config.TimesOfDay.Afternoon
import pherus.health.config.Config.TimesOfDay.Morning
import pherus.health.config.Config.returnMedicineIcons

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MedicineIntake(lazyState: LazyListState) {
    val schedules = mutableListOf(
        "morning", "noon", "evening"
    )

    val medicineList = mutableListOf(
        MedicineProps(
            medicineName = "Spironolactone",
            dosage = 200,
            recurrence = listOf(Daily, Weekly),
            endDate = "",
            timeOfDay = listOf(Morning, Afternoon),
            medicineForm = MedicineForm.pill,
            personalNotes = ""
        ),
        MedicineProps(
            medicineName = "Estradiol",
            dosage = 200,
            recurrence = listOf(Daily, Weekly),
            endDate = "",
            timeOfDay = listOf(Morning, Afternoon),
            medicineForm = MedicineForm.patch,
            personalNotes = ""
        ),
        MedicineProps(
            medicineName = "Spironolactone",
            dosage = 200,
            recurrence = listOf(Daily, Weekly),
            endDate = "",
            timeOfDay = listOf(Morning, Afternoon),
            medicineForm = MedicineForm.vial,
            personalNotes = ""
        ),
        MedicineProps(
            medicineName = "Spironolactone",
            dosage = 200,
            recurrence = listOf(Daily, Weekly),
            endDate = "",
            timeOfDay = listOf(Morning, Afternoon),
            medicineForm = MedicineForm.capsule,
            personalNotes = ""
        ),
        MedicineProps(
            medicineName = "Spironolactone",
            dosage = 200,
            recurrence = listOf(Daily, Weekly),
            endDate = "",
            timeOfDay = listOf(Morning, Afternoon),
            medicineForm = MedicineForm.transmiiter,
            personalNotes = ""
        ),
    )
    LazyColumn(
        state = lazyState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.medicine_cover),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Text(
                        text = "Medicine\nReminder",
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        modifier = Modifier
                            .align(alignment = Alignment.TopStart)
                            .padding(10.dp)
                    )
                }
            }
        }

        item {
            Text(
                text = "Schedules",
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                lineHeight = 28.sp,
                maxLines = 1
            )
        }

        item {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                schedules.forEachIndexed { _, s ->
                    ElevatedAssistChip(
                        onClick = { /*TODO*/ },
                        label = {
                            Text(
                                text = s,
                                fontWeight = FontWeight.Light,
                                fontSize = 14.sp,
                                lineHeight = 14.sp,
                                maxLines = 1
                            )
                        }
                    )
                }
            }
        }

        item {
            Text(
                text = "The day's list",
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                lineHeight = 28.sp,
                maxLines = 1
            )
        }

        items(medicineList.size) { index ->
            val item = medicineList[index]
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedIconButton(
                        onClick = {},
                        modifier = Modifier.size(85.dp),
                        border = BorderStroke(
                            5.dp,
                            MaterialTheme.colorScheme.primary
                        )
                    ) {
                        val iconResId = item.medicineForm?.let { returnMedicineIcons(it) }
                            ?: R.drawable.medicine_tablets
                        Image(
                            painter = // Resize to appropriate size
                            rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current).data(data = iconResId)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        size(1080) // Resize to appropriate size
                                        scale(Scale.FILL)
                                    }).build()
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "2 capsule, once per day",
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            maxLines = 1
                        )
                        Text(
                            text = item.medicineName.toString(),
                            fontWeight = FontWeight.Black,
                            fontSize = 28.sp,
                            lineHeight = 28.sp,
                            maxLines = 1
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            item.recurrence?.forEach { vle ->
                                ElevatedAssistChip(
                                    onClick = {},
                                    label = {
                                        Text(
                                            text = vle.name,
                                            fontWeight = FontWeight.Light,
                                            fontSize = 12.sp,
                                            lineHeight = 12.sp,
                                            maxLines = 1
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(
                                        MaterialTheme.colorScheme.onPrimary
                                    ),
                                    shape = RoundedCornerShape(100)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}