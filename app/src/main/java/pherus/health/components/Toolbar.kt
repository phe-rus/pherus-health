package pherus.health.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    TopAppBar(
        title = {
            Text(
                text = "Pherus Health",
                fontWeight = FontWeight.Black,
                fontSize = 23.sp,
                maxLines = 1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        actions = {
            FilledIconButton(
                onClick = {
                    scope.launch {
                        router.navigate("bio")
                    }
                },
            ) {
                Image(
                    painter = rememberAsyncImagePainter(basicInfor),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    )
}