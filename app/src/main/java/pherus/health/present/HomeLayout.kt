package pherus.health.present

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import pherus.health.components.Content
import pherus.health.components.Header
import pherus.health.components.Modules
import pherus.health.components.Toolbar
import pherus.health.ui.theme.PherusTheme

@Composable
fun HomeLayout() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { Toolbar() }
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it),
        ) {
            val parentHeight = maxHeight
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Header()
                }

                item {
                    Content()
                }

                item {
                    Row {
                        Text(
                            text = "User Modules",
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        )
                    }

                    Modules(parentHeight)
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