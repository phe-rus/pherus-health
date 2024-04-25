package pherus.health.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pherus.health.ui.theme.PherusTheme

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 10.dp , end = 10.dp)
    ) {
        Column(
            modifier = Modifier.weight(1.0F)
        ) {
            Text(text = "Health",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
            Text(text = "Overview",
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp
            )
        }

        Column(
            modifier = Modifier.weight(1.0F),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(text = "Upcoming Appointments",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(40)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .weight(0.7F)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(40)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "13")
                            Text(text = "WED",
                                fontWeight = FontWeight.Bold)
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1.3F)
                            .padding(start = 5.dp)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = "Paracetamol",
                            fontSize = 13.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Icon(
                                Icons.Rounded.AccountCircle,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                            Text(text = "9:30 am",
                                fontWeight = FontWeight.Light,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
fun HeaderPreview() {
    PherusTheme {
        Header()
    }
}