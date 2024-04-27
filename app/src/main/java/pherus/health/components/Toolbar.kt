package pherus.health.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar() {
    TopAppBar(
        title = {
            Text(
                text = "Good morning, la niina",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1
            )
        },
        navigationIcon = {
            Row(
                modifier = Modifier.padding(start = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilledIconButton(
                    onClick = {},
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(Icons.Rounded.AccountCircle, contentDescription = null)
                }
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                FilledIconButton(
                    onClick = {},
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(Icons.Rounded.Search, contentDescription = null)
                }
                FilledIconButton(
                    onClick = {},
                    modifier = Modifier.size(35.dp)
                ) {
                    Icon(Icons.Rounded.Notifications, contentDescription = null)
                }
            }
        }
    )
}