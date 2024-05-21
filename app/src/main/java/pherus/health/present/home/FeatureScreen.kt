package pherus.health.present.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Notes
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.PushPin
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pherus.health.viewModel.MainViewModel

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FeatureScreen(router: NavHostController, viewmodel: MainViewModel) {
    val scrollState = rememberScrollState()
    val coroutine = rememberCoroutineScope()
    val features = viewmodel.remoteCollection.collectAsState().value
    var menuDropdown by rememberSaveable { mutableStateOf(false) }

    val menuLists = mutableListOf(
        "All",
        "Others"
    )

    LaunchedEffect(Unit) {
        delay(100)
        viewmodel.initail()
    }

    FlowColumn(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state = scrollState, true)
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(modifier = Modifier.size(185.dp)) {
                Column(
                    modifier = Modifier.size(180.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(alignment = Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "8.8",
                                fontWeight = FontWeight.Bold,
                                fontSize = 38.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "health score",
                                    fontWeight = FontWeight.Light,
                                    color = Color.Gray
                                )
                                Icon(
                                    Icons.Rounded.Info,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                CircularProgressIndicator(
                    progress = { 0.8F },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 5.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onPrimary,
                    strokeCap = StrokeCap.Round,
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                ElevatedButton(onClick = {}) {
                    Text(text = "Plan check-up")
                }

                FilledIconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Rounded.Science, contentDescription = null)
                }

                FilledIconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Rounded.Money, contentDescription = null)
                }

                FilledIconButton(
                    onClick = {}
                ) {
                    Icon(Icons.AutoMirrored.Rounded.Notes, contentDescription = null)
                }
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = "Pherus Health assistant",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Pherus Health, your health score went down 28% last week",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    lineHeight = 13.sp
                )

                ElevatedAssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = "Let's discuss",
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    },
                    colors = AssistChipDefaults.elevatedAssistChipColors(
                        MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(100)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.7F),
            ) {
                Text(
                    text = "Health systems",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.weight(0.3F)
            ) {
                ElevatedAssistChip(
                    onClick = {
                        menuDropdown = true
                    },
                    label = {
                        Text(text = "All")
                    },
                    shape = RoundedCornerShape(100)
                )

                if (menuDropdown) {
                    DropdownMenu(
                        expanded = menuDropdown,
                        onDismissRequest = {
                            menuDropdown = false
                        },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        menuLists.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 12.sp,
                                        lineHeight = 12.sp
                                    )
                                },
                                onClick = {
                                    menuDropdown = false
                                }
                            )
                        }
                    }
                }
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        features?.filter { it.enabled }
            ?.forEachIndexed { _, healthModule ->
                Box(modifier = Modifier.fillMaxSize()) {
                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            coroutine.launch {
                                router.navigate(route = "modules/${healthModule.name}")
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(0.7F),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = {}) {
                                    Icon(Icons.Rounded.Science, contentDescription = null)
                                }
                                Column {
                                    Text(
                                        text = healthModule.name,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = healthModule.description,
                                        fontWeight = FontWeight.Light,
                                        fontSize = 8.sp,
                                        lineHeight = 8.sp,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = true,
                                        modifier = Modifier.padding(end = 10.dp)
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.weight(0.3F),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                ElevatedCard(
                                    modifier = Modifier
                                        .height(10.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}

                                ElevatedCard(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}

                                ElevatedCard(
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}

                                ElevatedCard(
                                    modifier = Modifier
                                        .height(50.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}

                                ElevatedCard(
                                    modifier = Modifier
                                        .height(5.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}

                                ElevatedCard(
                                    modifier = Modifier
                                        .height(15.dp)
                                        .width(8.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        MaterialTheme.colorScheme.primary
                                    )
                                ) {}
                            }
                        }
                    }

                    if (healthModule.enabled) {
                        Icon(
                            Icons.Rounded.PushPin,
                            contentDescription = null,
                            modifier = Modifier
                                .align(alignment = Alignment.TopEnd)
                                .padding(5.dp)
                                .rotate(30F)
                        )
                    }
                }
            }

        Spacer(modifier = Modifier.size(20.dp))
    }
}