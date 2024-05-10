package pherus.health.present

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Face2
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.MedicalInformation
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.components.Footer
import pherus.health.ui.theme.PherusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileLayout(router: NavHostController) {
    val coroutine = rememberCoroutineScope()

    val patientsInformation = mutableListOf(
        "Preferred Name",
        "Date Of Birth",
        "Email Address",
        "Phone Number",
        "Full Name"
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Settings",
                            fontWeight = FontWeight.Bold,
                            fontSize = 23.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutine.launch {
                                router.navigate("home")
                            }
                        },
                        modifier = Modifier.size(35.dp)
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            contentPadding = it,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(20)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(alignment = Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MaterialTheme.colorScheme.surfaceContainer.copy(
                                                alpha = 0.0f
                                            ),
                                            MaterialTheme.colorScheme.surfaceContainer.copy(
                                                alpha = 0.2f
                                            ),
                                            MaterialTheme.colorScheme.surfaceContainer.copy(
                                                alpha = 0.6f
                                            ),
                                            MaterialTheme.colorScheme.surfaceContainer.copy(
                                                alpha = 1.0f
                                            )
                                        )
                                    )
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilledIconButton(
                                    onClick = {},
                                    modifier = Modifier.size(55.dp)
                                ) {
                                    Icon(Icons.Rounded.Face2, contentDescription = null)
                                }

                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Patients Name",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "patients@email.com",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                ElevatedCard(
                    shape = RoundedCornerShape(100),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "1EG$-TE5-MK72",
                                fontWeight = FontWeight.Black
                            )
                            Icon(
                                Icons.Rounded.Info,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                        Text(
                            text = "12/12/2027",
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Contact Information",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    patientsInformation.forEach { item ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(34)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    Icons.Rounded.MedicalInformation,
                                    contentDescription = null
                                )
                                Column {
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "Clinical Profile",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(5.dp)
                    )

                    patientsInformation.forEach { item ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(34)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 10.dp,
                                        bottom = 10.dp
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    Icons.Rounded.MedicalInformation,
                                    contentDescription = null
                                )
                                Column {
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = item,
                                        fontWeight = FontWeight.Light
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Your Videos & Audios",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            items(1) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(34)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(Icons.Rounded.MedicalInformation, contentDescription = null)
                        Text(text = "No emergency videos")
                    }
                }
            }

            item {
                Text(
                    text = "Help & Support",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            items(5) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(34)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(Icons.Rounded.MedicalInformation, contentDescription = null)
                        Text(text = "Medical Information")
                    }
                }
            }

            item {
                Footer()
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE, showSystemUi = true, showBackground = true
)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    PherusTheme {
        ProfileLayout(router = navController)
    }
}