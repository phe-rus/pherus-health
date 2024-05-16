package pherus.health.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Emergency
import androidx.compose.material.icons.outlined.Face2
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Emergency
import androidx.compose.material.icons.rounded.Face2
import androidx.compose.material.icons.rounded.HealthAndSafety
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomHelper(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNotification: Boolean,
    val badgeCount: Int? = null
)

@Composable
fun Bottombars(
    onvaluechanged: (Int) -> Unit
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val items = mutableListOf(
        BottomHelper(
            title = "Home",
            selectedIcon = Icons.Rounded.Emergency,
            unselectedIcon = Icons.Outlined.Emergency,
            hasNotification = false
        ),
        BottomHelper(
            title = "Features",
            selectedIcon = Icons.Rounded.HealthAndSafety,
            unselectedIcon = Icons.Outlined.HealthAndSafety,
            hasNotification = false
        ),
        BottomHelper(
            title = "Notification",
            selectedIcon = Icons.Rounded.Notifications,
            unselectedIcon = Icons.Outlined.Notifications,
            hasNotification = true,
            badgeCount = 34
        ),
        BottomHelper(
            title = "Your info",
            selectedIcon = Icons.Rounded.Face2,
            unselectedIcon = Icons.Outlined.Face2,
            hasNotification = false
        )
    )

    LaunchedEffect(selectedTab) {
        onvaluechanged(selectedTab)
    }

    DisposableEffect(selectedTab) {
        onDispose {
            onvaluechanged(selectedTab)
        }
    }

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick = {
                    selectedTab = index
                },
                label = {
                    Text(text = item.title)
                },
                icon = {
                    BadgedBox(
                        badge = {
                            when {
                                item.badgeCount != null -> {
                                    Badge {
                                        Text(text = item.badgeCount.toString())
                                    }
                                }

                                item.hasNotification -> {
                                    Badge()
                                }
                            }
                        }
                    ) {
                        Icon(
                            if (index == selectedTab) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}