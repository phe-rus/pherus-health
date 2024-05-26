package pherus.health.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DoNotDisturbOnTotalSilence
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pherus.health.config.Config

@Composable
fun ProfileBar(
    router: NavHostController,
    coroutine: CoroutineScope,
    profileInformtion: Config.PatientInformation?,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.Inherit,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onDismissRequest()
            }) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.TopCenter)
                    .padding(10.dp)
                    .clickable(enabled = false, onClick = {}),
                shape = RoundedCornerShape(5),
                elevation = CardDefaults.elevatedCardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onDismissRequest) {
                            Icon(Icons.Rounded.Close, contentDescription = null)
                        }

                        Text(
                            text = "Pherus Health",
                            fontWeight = FontWeight.Black,
                            fontSize = 28.sp,
                            lineHeight = 28.sp
                        )
                    }

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp,
                            bottomStart = 10.dp,
                            bottomEnd = 10.dp
                        ),
                        elevation = CardDefaults.elevatedCardElevation(1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedIconButton(
                                    onClick = {},
                                    modifier = Modifier.size(45.dp)
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(profileInformtion?.basicInformations?.avatarHolder.toString()),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .fillMaxSize()
                                    )
                                }

                                Column(
                                    modifier = Modifier,
                                ) {
                                    Text(
                                        text = profileInformtion?.basicInformations?.preferedName
                                            ?: "Prefered Name",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        lineHeight = 15.sp
                                    )
                                    Text(
                                        text = profileInformtion?.contactInformation?.email
                                            ?: "healht.pherus@pherus.org",
                                        fontWeight = FontWeight.Light,
                                        fontSize = 14.sp,
                                        lineHeight = 14.sp
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = {
                                    coroutine.launch {
                                        onDismissRequest()
                                        router.navigate("bio")
                                    }
                                },
                                shape = RoundedCornerShape(20)
                            ) {
                                Text(
                                    text = "Manage your Pherus Health Account",
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp,
                                    lineHeight = 13.sp
                                )
                            }
                        }
                    }

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            topStart = 10.dp,
                            topEnd = 10.dp,
                            bottomStart = 30.dp,
                            bottomEnd = 30.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(0.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Manage your Pherus Health Account",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 13.sp,
                                    lineHeight = 13.sp
                                )
                            }
                            Button(
                                onClick = {},
                                shape = RoundedCornerShape(100),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Manage your Pherus Health Account",
                                    fontWeight = FontWeight.Light,
                                    fontSize = 13.sp,
                                    lineHeight = 13.sp
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Privacy Policy",
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                        Icon(
                            Icons.Rounded.DoNotDisturbOnTotalSilence,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "Terms of Service",
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp,
                            lineHeight = 12.sp
                        )
                    }
                }
            }
        }
    }
}