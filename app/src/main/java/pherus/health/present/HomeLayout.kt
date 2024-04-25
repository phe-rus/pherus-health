package pherus.health.present

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import pherus.health.ui.theme.PherusTheme

@Composable
fun HomeLayout() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Testing")
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