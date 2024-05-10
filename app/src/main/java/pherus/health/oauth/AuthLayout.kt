package pherus.health.oauth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.PasswordHolder

@Composable
fun AuthLayout(router: NavHostController) {
    val localFocusManager = LocalFocusManager.current
    val coroutine = rememberCoroutineScope()

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
                Text(
                    text = "Pherus Health",
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp,
                    lineHeight = 28.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = "Please Login with your email and password below to continue into your Pherus Health Account",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                )

                InputHolder(
                    header = "Email address",
                    onValueChange = {},
                )

                PasswordHolder(
                    header = "Password",
                    onValueChange = {},
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Forgot Password?",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        minLines = 1,
                        modifier = Modifier.clickable {
                            coroutine.launch {
                                router.navigate("forgot")
                            }
                        }
                    )

                    Text(
                        text = "Don't have an account?",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        minLines = 1,
                        modifier = Modifier.clickable {
                            coroutine.launch {
                                router.navigate("register")
                            }
                        }
                    )
                }

                ElevatedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(text = "Login")
                }

                ElevatedCard {
                    Text(
                        text = "Note:\nBy login in to Pherus Health you hereby agree to the Pherus Terms and conditions of the services provided by Pherus Health under Pherus .co smc limited.",
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}