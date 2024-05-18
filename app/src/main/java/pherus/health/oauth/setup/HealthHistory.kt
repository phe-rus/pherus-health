package pherus.health.oauth.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pherus.health.oauth.Authobjects.GeneralHistoryProp
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HealthHistory(healthHistoryCollection: MutableList<GeneralHistoryProp>) {
    FlowColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        healthHistoryCollection.forEachIndexed { _, generalHistoryProp ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Titles(
                        title = generalHistoryProp.title
                    )
                    if (generalHistoryProp.subTitle.isNullOrEmpty()) {
                        SubTitles(subtitle = generalHistoryProp.subTitle.toString())
                    }
                }

                generalHistoryProp.list.forEachIndexed { _, healthHistoryProp ->
                    Questionary(
                        title = healthHistoryProp.title,
                        questions = healthHistoryProp.list
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Questionary(
    title: String,
    questions: List<String>
) {
    var selected by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            lineHeight = 16.sp
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            maxItemsInEachRow = 3
        ) {
            questions.forEachIndexed { index, s ->
                ElevatedFilterChip(
                    selected = selected.contains(s),
                    onClick = {
                        selected = s
                    },
                    label = {
                        Text(
                            text = s,
                            fontWeight = FontWeight.Light,
                            fontSize = 13.sp,
                            lineHeight = 13.sp,
                            maxLines = 1
                        )
                    },
                    shape = RoundedCornerShape(100)
                )
            }
        }

        if (selected.contains("others", true)) {
            InputHolder(
                header = "Specify",
                onValueChange = {}
            )
        }
    }
}