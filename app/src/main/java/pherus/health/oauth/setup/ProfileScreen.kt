package pherus.health.oauth.setup

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrowseGallery
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pherus.health.oauth.Authobjects
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles

@SuppressLint("MutableCollectionMutableState")
@Composable
fun ProfileScreen(profileAvatar: Int, profileCollections: MutableList<Authobjects.ProfileProps>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedIconButton(
                onClick = {},
                modifier = Modifier
                    .size(160.dp)
                    .padding(10.dp)
                    .align(alignment = Alignment.Center)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = profileAvatar),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    FilledIconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(10.dp)
                            .align(alignment = Alignment.Center),
                        shape = RoundedCornerShape(42)
                    ) {
                        Icon(Icons.Rounded.BrowseGallery, contentDescription = null)
                    }
                }
            }
        }
        profileCollections.forEachIndexed { _, profileProps ->
            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Titles(
                            title = profileProps.title,
                        )

                        SubTitles(
                            subtitle = profileProps.subtitle,
                        )
                    }

                    profileProps.list.forEachIndexed { _, s ->
                        InputHolder(
                            header = s.value,
                            onValueChange = s.onchangelistener
                        )
                    }
                }
            }
        }
    }
}