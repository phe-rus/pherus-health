package pherus.health.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pherus.health.config.Config.CollectionProps

@Composable
fun Content(
    parentHeight: Int,
    route: NavHostController,
    coroutine: CoroutineScope,
    collections: State<List<CollectionProps>?>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = "Collection",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(4),
            contentPadding = PaddingValues(start = 5.dp, end = 5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = parentHeight.dp)
        ) {
            items(collections.value?.size ?: 0) { index ->
                val item = collections.value?.get(index)
                ElevatedCard(
                    modifier = Modifier.padding(2.dp),
                    shape = RoundedCornerShape(40),
                    onClick = {
                        coroutine.launch {
                            route.navigate("services/${item?.name}")
                        }
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        IconButton(
                            onClick = {},
                            modifier = Modifier.size(60.dp),
                        ) {
                            Image(
                                painter = // Resize to appropriate size
                                rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(data = item?.image)
                                        .apply(block = fun ImageRequest.Builder.() {
                                            size(720) // Resize to appropriate size
                                            scale(Scale.FILL)
                                        }).build()
                                ),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                        Text(
                            text = item?.name.toString(),
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }
        }
    }
}