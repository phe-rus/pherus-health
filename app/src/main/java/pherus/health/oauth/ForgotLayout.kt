package pherus.health.oauth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import pherus.health.oauth.Authobjects.InputHolder
import pherus.health.oauth.Authobjects.SubTitles
import pherus.health.oauth.Authobjects.Titles

@Composable
fun ForgotLayout(router: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(paddingValues = it)
                .fillMaxSize()
        ) {
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