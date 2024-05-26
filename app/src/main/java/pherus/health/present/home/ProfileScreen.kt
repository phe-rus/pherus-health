package pherus.health.present.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pherus.health.viewModel.MainViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(scrollstate: ScrollState, viewmodel: MainViewModel) {
    val profileInformtion = viewmodel.usrCollection.collectAsState().value

    val profileDetails = mutableListOf(
        "Preferred Name" to profileInformtion?.basicInformations?.preferedName,
        "Email Address" to profileInformtion?.contactInformation?.email,
        "Phone Number" to profileInformtion?.contactInformation?.phoneNumber,
        "Emergency Number" to "default is 911"
    )
    val emergencyDetails = mutableListOf(
        "Help & Support" to "",
        "Send feedback" to "",
        "Partnership and Sponsors" to "",
        "Open source licenses" to ""
    )

    LaunchedEffect(Unit) {
        delay(100)
        viewmodel.initail()
    }

    FlowColumn(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollstate, true)
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Emergency Information",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.padding(start = 10.dp)
            )

            profileDetails.forEachIndexed { _, pair ->
                ProfileDisplay(
                    title = pair.first,
                    value = pair.second.toString()
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Pherus Health Pay",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.padding(start = 10.dp)
            )

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Approximate Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                    Text(text = profileInformtion?.contactInformation?.countryResident ?: "Unknown")
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Your videos",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.padding(start = 10.dp)
            )

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Emergency Evidence Recording's",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                    Text(text = "non")
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Help & support",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 1,
                modifier = Modifier.padding(start = 10.dp)
            )

            emergencyDetails.forEachIndexed { _, pair ->
                ProfileDisplay(
                    title = pair.first,
                    value = pair.second
                )
            }
        }

        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun ProfileDisplay(
    title: String,
    value: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Black,
                fontSize = 14.sp,
                maxLines = 1
            )
            Text(
                text = value,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                maxLines = 1
            )
        }
    }
}