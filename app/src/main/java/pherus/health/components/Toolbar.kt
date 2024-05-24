package pherus.health.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(
    router: NavHostController,
    scope: CoroutineScope,
    basicInfor: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Pherus Health",
                fontWeight = FontWeight.Black,
                fontSize = 23.sp,
                maxLines = 1
            )
        },
        actions = {
            OutlinedIconButton(
                onClick = {
                    scope.launch {
                        router.navigate("bio")
                    }
                },
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                ),
            ) {
                Image(
                    painter = rememberAsyncImagePainter(basicInfor),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}