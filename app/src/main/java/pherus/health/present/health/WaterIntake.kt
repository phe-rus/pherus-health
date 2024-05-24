package pherus.health.present.health

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WaterIntake() {
    FlowColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.align(alignment = Alignment.Center)) {
                CircularProgressIndicator(
                    progress = { 0.1F },
                    modifier = Modifier
                        .size(250.dp)
                        .align(alignment = Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    strokeWidth = 20.dp,
                    strokeCap = StrokeCap.Round
                )

                Column(
                    modifier = Modifier.align(alignment = Alignment.Center)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontSize = 38.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("100")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            ) {
                                append("ml")
                            }
                        }
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Water Intake")
            }
        }
    }
}