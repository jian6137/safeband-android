package com.inclusitech.safeband.ui.views.main

import android.content.Intent
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
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
fun ConnectDeviceNFCCheck(
    navHostController: NavHostController,
    mainVMService: MainVMService
) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.switch_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val checkComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.check_anim))
    val checkProgress by animateLottieCompositionAsState(
        composition = checkComposition,
        iterations = LottieConstants.IterateForever
    )

    var isNFCEnabled by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (mainVMService.getNFCStatus(context) == "NFC_ENABLED") {
                    isNFCEnabled = true
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
                composition = if(isNFCEnabled) checkComposition else composition,
                progress = {
                    if(isNFCEnabled) checkProgress else progress
                },
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                text = if (isNFCEnabled) "NFC Enabled" else "NFC is Disabled",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = if (isNFCEnabled) "Your device's NFC is now enabled." else "Your device's NFC is disabled. Please enable NFC to continue connecting with SafeBand.",
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
                if (isNFCEnabled) {
                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        onClick = {
                            navHostController.navigate("${MainRoutes.ConnectDeviceNFC.route}/READ/0")
                        },
                    ) {
                        Text("Continue")
                    }
                } else {
                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth(),
                        onClick = {
                            val intent = Intent(android.provider.Settings.ACTION_NFC_SETTINGS)
                            context.startActivity(intent)
                        },
                    ) {
                        Text("Enable")
                    }
                }
            }
        }
    }
}