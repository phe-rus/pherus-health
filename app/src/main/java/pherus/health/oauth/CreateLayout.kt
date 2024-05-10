package pherus.health.oauth

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.PasswordHolder
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles

@Composable
fun CreateLayout(router: NavHostController) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var localFocusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = it)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        localFocusManager.clearFocus()
                    })
                }
        ) {
            Icon(
                painter = painterResource(id = pherus.health.R.drawable.pherus),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(alignment = Alignment.TopEnd)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(title = "Create an Account")
                SubTitles(subtitle = "To create a Pherus Health account please provide a valid email address and a secure strong password and a user name to continue.")

                InputHolder(
                    header = "Preferred Name",
                    onValueChange = {},
                )

                InputHolder(
                    header = "Email address",
                    onValueChange = {},
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )
                PasswordHolder(
                    header = "Password",
                    onValueChange = {},
                )

                PasswordHolder(
                    header = "Repeat Password",
                    onValueChange = {},
                )

                ElevatedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(text = "Create Account")
                }
            }
        }
    }
}