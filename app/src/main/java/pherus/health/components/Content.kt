package pherus.health.components

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import pherus.health.ui.theme.PherusTheme

@Composable
fun Content() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 5.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(text = "Collection")
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "View all")
                Icon(
                    Icons.Rounded.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState(), true),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .width(200.dp)
                    .height(130.dp),
                shape = RoundedCornerShape(30)
            ) {

            }

            ElevatedCard(
                modifier = Modifier
                    .width(200.dp)
                    .height(130.dp),
                shape = RoundedCornerShape(30)
            ) {

            }

            ElevatedCard(
                modifier = Modifier
                    .width(200.dp)
                    .height(130.dp),
                shape = RoundedCornerShape(30)
            ) {

            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.RED_DOMINATED_EXAMPLE
)
@Composable
fun ContentPreview() {
    PherusTheme {
        Content()
    }
}