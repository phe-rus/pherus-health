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
import androidx.compose.material.icons.rounded.Science
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureScreen() {
    val scrollState = rememberScrollState()
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
                ElevatedCard(
                    modifier = Modifier.size(180.dp),
                    shape = RoundedCornerShape(100)
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
                    strokeWidth = 15.dp,
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

                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Rounded.Science, contentDescription = null)
                }

                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Rounded.Money, contentDescription = null)
                }

                IconButton(
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
            Text(
                text = "Health systems",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            ElevatedAssistChip(
                onClick = {},
                label = {
                    Text(text = "All")
                },
                shape = RoundedCornerShape(100)
            )
        }


        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
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
                            text = "Hello world",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hello world",
                            fontWeight = FontWeight.Light,
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

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Hello world")
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Hello world")
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Hello world")
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Hello world")
            }
        }

        OutlinedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Hello world")
            }
        }

        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun HealthUtilities(
    icon: String,
    title: String,
) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Hello world")
        }
    }
}