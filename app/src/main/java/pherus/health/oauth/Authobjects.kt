package pherus.health.oauth

import android.annotation.SuppressLint
import androidx.annotation.Keep
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pherus.health.components.WideCalendar
import pherus.health.config.Validation.isStrongPassword
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Authobjects {
    @Composable
    fun InputHolder(
        header: String,
        defaultValue: String? = "",
        onValueChange: (String) -> Unit
    ) {
        var showCalander by rememberSaveable { mutableStateOf(false) }
        var value by rememberSaveable { mutableStateOf(defaultValue.toString()) }
        LaunchedEffect(value) {
            onValueChange(value)
        }
        DisposableEffect(Unit) {
            onDispose {
                value = ""
            }
        }

        OutlinedTextField(
            value = value,
            onValueChange = { input -> value = input },
            placeholder = {
                Text(
                    text = header,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    maxLines = 1
                )
            },
            readOnly = checkforSpecial(header, "date of birth"),
            textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 12.sp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            interactionSource = remember { MutableInteractionSource() }
                .also { interactionSource ->
                    LaunchedEffect(interactionSource) {
                        interactionSource.interactions.collect {
                            if (it is PressInteraction.Release) {
                                if (checkforSpecial(header, "date of birth")) {
                                    showCalander = true
                                }
                            }
                        }
                    }
                }
        )

        if (showCalander) {
            WideCalendar(
                closeSelection = {
                    showCalander = false
                },
                selectedValue = { ldate ->
                    value = ldate
                }
            )
        }
    }

    fun checkforSpecial(
        header: String,
        vlr: String
    ): Boolean {
        return header.contains(vlr, true)
    }

    @Composable
    fun PasswordHolder(
        header: String, onValueChange: (String) -> Unit
    ) {
        var password by rememberSaveable { mutableStateOf("") }
        var passwordVisible by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(password) {
            onValueChange(password)
        }

        DisposableEffect(Unit) {
            onDispose {
                password = ""
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            OutlinedTextField(
                value = password,
                onValueChange = { psw -> password = psw },
                placeholder = {
                    Text(
                        text = header,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        maxLines = 1
                    )
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Rounded.Visibility
                    else Icons.Rounded.VisibilityOff
                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
            )
            if (!header.contains(
                    "Repeat Password",
                    true
                ) && password.isNotEmpty() && !isStrongPassword(password)
            ) {
                Text(
                    text = "Please make a stronger password: at least 8 characters, one uppercase letter, one lowercase letter, and one number",
                    fontWeight = FontWeight.Light,
                    fontSize = 8.sp,
                    lineHeight = 8.sp
                )
            }
        }
    }

    @Keep
    @Immutable
    data class LoginState(
        val email: String? = null,
        val password: String? = null,
        val onListener: () -> Unit,
        val onErrorMessage: (String) -> Unit
    ) : Serializable

    @Composable
    fun Titles(title: String) {
        Text(
            text = title,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun SubTitles(subtitle: String) {
        Text(
            text = subtitle,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            lineHeight = 12.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )
    }

    @SuppressLint("NewApi")
    fun getCurrentDate(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("MMMM-dd-yyyy") // Customize the format here
        return now.format(formatter)
    }

    @Keep
    @Immutable
    data class ProfileProps(
        val key: String? = null,
        val title: String,
        val subtitle: String,
        val list: MutableList<ValueProps>
    ) : Serializable

    @Keep
    @Immutable
    data class ValueProps(
        val key: String? = null,
        val value: String,
        val onchangelistener: (String) -> Unit
    ) : Serializable

    @Keep
    @Immutable
    data class HealthHistoryProp(
        val title: String,
        val list: MutableList<String>,
        val onValueChange: (String) -> Unit? = {}
    ) : Serializable

    @Keep
    @Immutable
    data class GeneralHistoryProp(
        val title: String,
        val subTitle: String? = null,
        val list: MutableList<HealthHistoryProp>,
    ) : Serializable
}