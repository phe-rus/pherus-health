package pherus.health.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import pherus.health.ui.theme.PherusTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Pharmacies() {
    val coroutine = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val data = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Pharmaceuticals",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            ElevatedAssistChip(
                onClick = {},
                label = { Text(text = "Seen more") },
                trailingIcon = {
                    Icon(
                        Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                },
                shape = RoundedCornerShape(100)
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(state = scrollState, true),
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                data.forEachIndexed { _, s ->
                    ElevatedCard(
                        modifier = Modifier
                            .height(180.dp)
                            .width(250.dp)
                    ) {

                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }

            if (scrollState.value != 0) {
                FilledIconButton(
                    onClick = {
                        coroutine.launch {
                            scrollState.scrollTo(value = 0)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(5.dp)
                ) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
                }
            }

            if (scrollState.value > 0) {
                FilledIconButton(
                    onClick = {
                        coroutine.launch {
                            scrollState.scrollTo(value = scrollState.maxValue)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(5.dp)
                ) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowForwardIos, contentDescription = null)
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true, wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PharmaciesPreview() {
    PherusTheme {
        Pharmacies()
    }
}