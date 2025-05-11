package com.inclusitech.safeband.ui.views.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.ui.core.MainRoutes

@Composable
fun AddDeviceCustomize(navHostController: NavHostController, mainVMService: MainVMService) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.customize_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    val context = LocalContext.current

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
                    .height(124.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = if(mainVMService.getAccountRole(context) == "PATIENT") "Add Yourself" else "Setup For Your Patient",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = if(mainVMService.getAccountRole(context) == "PATIENT") "Your current patient information will be saved to your SafeBand wristband" else "The patient information you selected that is linked with your SafeBand caregiver account will be saved to your target SafeBand wristband",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(64.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(start = 24.dp, end = 24.dp)
            ) {
                Button(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    onClick = {
                        if(mainVMService.currentRegisteredTag != ""){
                            navHostController.navigate("${MainRoutes.ConnectDeviceNFC.route}/WRITE/${mainVMService.currentRegisteredTag}}")
                        } else {
                            navHostController.navigate(MainRoutes.DeviceAddComplete.route)
                        }
                    },
                ) {
                    Text("Save")
                }
            }
        }
    }
}