package pherus.health.present

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pherus.health.ui.theme.PherusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeLayout() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Good morning, la niina",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    FilledIconButton(onClick = {}) {
                        Icon(Icons.Rounded.AccountCircle, contentDescription = null)
                    }
                },
                actions = {
                    FilledIconButton(onClick = {}) {
                        Icon(Icons.Rounded.Search, contentDescription = null)
                    }
                    FilledIconButton(onClick = {}) {
                        Icon(Icons.Rounded.Notifications, contentDescription = null)
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
               Row(
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Column(
                       modifier = Modifier.weight(1.0F)
                   ) {
                       Text(text = "Health",
                           fontWeight = FontWeight.Bold,
                           fontSize = 40.sp
                       )
                       Text(text = "Overview",
                           fontWeight = FontWeight.Bold,
                           fontSize = 40.sp
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
                           modifier = Modifier.fillMaxWidth()
                               .height(55.dp)
                       ) {
                           Row(
                               modifier = Modifier.fillMaxWidth()
                           ) {
                               ElevatedCard(
                                   modifier = Modifier.weight(0.7F)
                                       .fillMaxHeight()
                               ) {
                                   Column(
                                       modifier = Modifier.fillMaxWidth()
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
                                       .padding(2.dp)
                                       .fillMaxHeight(),
                                   verticalArrangement = Arrangement.Center
                               ) {
                                   Text(text = "Paracetamol")
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
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)

@Composable
fun HomeLayoutPreview() {
    PherusTheme {
        HomeLayout()
    }
}