package pherus.health.oauth.setup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import pherus.health.R
import pherus.health.oauth.Authobjects.Titles

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConsentScreen() {
    val coroutine = rememberCoroutineScope()
    var isChecked by rememberSaveable { mutableStateOf(false) }

    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append("Our End User Agreement and Privacy Policy are straightforward and simple. You can read more details by clicking the links below:\n\n")
        pushStringAnnotation(tag = "URL", annotation = "https://pherus.org/legal/privacy/")
        withStyle(style = SpanStyle(
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline
        ), block = {
            append("Privacy Policy\n\n")
        })
        pop()
        pushStringAnnotation(tag = "URL", annotation = "https://pherus.org/legal/eula")
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("End User License Agreement\n")
        }
        pop()
        append("\nAs Pherus Health is a part of Pherus, we at Pherus are dedicated to upholding your privacy and protecting your personal information. This privacy policy outlines how we collect, use, and safeguard your data across all our products and services. We strive to maintain transparency, trust, and respect for your privacy rights.")
    }

    FlowColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(
                    title = "${stringResource(id = R.string.app_name)} Health"
                )
                Text(
                    text = stringResource(id = R.string.description),
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(
                    title = "End User Agreement"
                )
                Text(
                    text = "",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )

                Text(
                    text = annotatedString,
                    fontWeight = FontWeight.Light,
                    fontSize = 13.sp,
                    lineHeight = 13.sp,
                    modifier = Modifier.clickable {
                        annotatedString.getStringAnnotations("URL", 0, annotatedString.length)
                            .firstOrNull()?.let { stringAnnotation ->
                                uriHandler.openUri(stringAnnotation.item)
                            }
                    }
                )
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(
                    title = "Legal Consent"
                )
                Text(
                    text = "At Pherus Health, we are committed to transparency and honesty, even when it might be uncomfortable. Our dedication to the truth—one that aligns with reality—underscores our mission. We are steadfast in our commitment to protecting your information. Your data will never be accessed without your explicit consent, unless legally required by a warrant. All sensitive information, including your name and other personal details, is securely protected and only disclosed to authorized individuals when necessary and with your consent. We prioritize your privacy and ensure that your information remains confidential. Your legal name is never shared with any third parties unless is a medical processed that requires your legal name with your consent either than that you preferred/acquired name is your representational legal name in most Pherus Health services.",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }

        ElevatedCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Titles(
                    title = "Security and Privacy"
                )
                Text(
                    text = "We are committed to protecting your information. Your data will never be accessed without your explicit consent, unless legally required by a warrant. All sensitive information, including your name and other personal details, is securely protected and only disclosed to authorized individuals when necessary and with your consent. Pherus Employees multiple encryption methods to protect your information from, our selves and from others and continuously improving on it.",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
            }
        }

        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "I Trust Pherus Health under Pherus with my information",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                lineHeight = 12.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "I Agree",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                )
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked = it
                    }
                )
            }
        }


        Button(
            onClick = {
                coroutine.launch {
                    //TODO
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10)
        ) {
            Text(text = "I consent to continue")
        }
    }
}