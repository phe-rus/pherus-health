package pherus.health.oauth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles

@Composable
fun ForgotLayout(router: NavHostController) {
    val coroutine = rememberCoroutineScope()
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                }
        ) {
            FilledIconButton(
                onClick = {
                    coroutine.launch {
                        router.navigate("auth")
                    }
                },
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .padding(10.dp)
            ) {
                Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = null)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.Center)
                    .padding(10.dp)
            ) {
                Titles(title = "Forgot Password")
                SubTitles(subtitle = "Please Input your email address and link will be sent to your email to reset your password")
                InputHolder(
                    header = "Email address",
                    onValueChange = {},
                )
                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(text = "Reset Password")
                }
            }
        }
    }
}