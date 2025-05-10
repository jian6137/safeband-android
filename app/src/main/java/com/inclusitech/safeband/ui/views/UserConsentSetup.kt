package com.inclusitech.safeband.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.core.SetupRoutes

@Composable
fun CollectedUserData() {
    val locationSwitch by remember { mutableStateOf(true) }
    val homeAddressSwitch by remember { mutableStateOf(false) }
    var emergencyContactSwitch by remember { mutableStateOf(false) }
    var medicalRecordsSwitch by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(text = "Medical Records")
                    Text(
                        text = "Handled by your patient's doctor *",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
                Switch(
                    checked = medicalRecordsSwitch,
                    onCheckedChange = { medicalRecordsSwitch = it },
                    enabled = false
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(text = "Location")

                }
                Switch(
                    checked = locationSwitch,
                    onCheckedChange = {},
                    enabled = false
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .wrapContentWidth()
                ) {
                    Text(text = "Home Address")
                    Text(
                        text = "Required *",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
                Switch(
                    checked = homeAddressSwitch,
                    onCheckedChange = {},
                    enabled = false
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Emergency Contact List")
                Switch(
                    checked = emergencyContactSwitch,
                    onCheckedChange = { emergencyContactSwitch = it },
                    enabled = true
                )
            }


        }
    }
}

@Composable
fun UserConsentSetup(navHostController: NavHostController, setupVMService: SetupVMService) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.privacy_anim))
    val progress by animateLottieCompositionAsState(
        composition,
    )

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(250.dp)
            )
            Text(text = "Before We Continue", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "You can review and manage the personal data you share with Outbreak Sentinel that can be shared with Emergency Responders to know more about you.",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            CollectedUserData()
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = WindowInsets.systemGestures.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                onClick = {
                    navHostController.navigate(SetupRoutes.AuthenticationSetup.route)
                },
                enabled = setupVMService.name.value.isNotEmpty()
            ) {
                Text("Next")
            }
        }
    }
}