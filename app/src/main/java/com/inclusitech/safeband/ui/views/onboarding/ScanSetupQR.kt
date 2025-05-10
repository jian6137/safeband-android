package com.inclusitech.safeband.ui.views.onboarding

import android.app.Activity
import android.view.View
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemGestures
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import java.util.Random
import com.inclusitech.safeband.R
import com.inclusitech.safeband.ui.core.SetupRoutes
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import androidx.core.net.toUri
import com.inclusitech.safeband.core.vms.SetupVMService

@Composable
fun SelectPhotoView(navHostController: NavHostController, setupVMService: SetupVMService) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.qr_scan_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    var scanFlag by rememberSaveable { mutableStateOf(true) }
    var showResult by rememberSaveable { mutableStateOf(false) }
    var lastReadQR by rememberSaveable { mutableStateOf<String>("") }
    val recomposeFlag by rememberSaveable { mutableIntStateOf(Random().nextInt()) }

    var isStartScanning by rememberSaveable { mutableStateOf(false) }

    val easeInOutQuart = remember { CubicBezierEasing(0.77f, 0.0f, 0.18f, 1.0f) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(124.dp)
            )
            Crossfade(
                targetState = isStartScanning, label = "scannerCrossfadeAnimation",
                animationSpec = tween(durationMillis = 500, easing = easeInOutQuart)
            )
            {
                when (it) {
                    true -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .wrapContentHeight()
                                    .widthIn(max = 350.dp)
                                    .heightIn(max = 350.dp),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                key(recomposeFlag) {
                                    AndroidView(
                                        factory = { context ->
                                            val preview = DecoratedBarcodeView(context)
                                            preview.viewFinder.visibility = View.GONE
                                            preview.setStatusText("")
                                            preview.cameraSettings.isAutoTorchEnabled = false
                                            preview.cameraSettings.isAutoFocusEnabled = true
                                            preview.apply {
                                                val capture =
                                                    CaptureManager(context as Activity, this)
                                                capture.initializeFromIntent(context.intent, null)
                                                capture.decode()
                                                this.decodeContinuous { result ->
                                                    if (!scanFlag) {
                                                        return@decodeContinuous
                                                    }
                                                    scanFlag = false
                                                    result.text?.let {

                                                        if (lastReadQR.isEmpty()) {
                                                            if (result.text.startsWith("https://safeband.zaide.online/u?q=")) {
                                                                val uri = result.text.toUri()
                                                                val patientID = uri.getQueryParameter("q")
                                                                lastReadQR = result.text
                                                                navHostController.navigate(
                                                                    "${SetupRoutes.UserProvisionView.route}/${patientID}"
                                                                )
                                                            }
                                                        }

                                                        scanFlag = true
                                                        showResult = true
                                                    }
                                                }
                                                this.resume()
                                            }
                                        },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                            Text(
                                text = "Start Scanning",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 16.dp),
                            )
                            Text(
                                text = "Try to fit the QR Code presented to you by your patient's doctor in the frame.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 2.dp, start = 16.dp, end = 16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    false -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .wrapContentHeight()
                            ) {
                                LottieAnimation(
                                    composition = composition,
                                    progress = {
                                        progress
                                    },
                                    modifier = Modifier
                                        .size(250.dp)
                                        .align(Alignment.Center)
                                )
                            }
                            Text(
                                text = "Hello, ${setupVMService.name.value}!",
                                style = MaterialTheme.typography.displaySmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    top = 16.dp,
                                    start = 16.dp,
                                    end = 16.dp
                                ),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Prepare to set up your family member's or patient's SafeBand account. To begin, please scan the QR code provided by their doctor.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            if (!isStartScanning) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 12.dp,
                            bottom = WindowInsets.systemGestures.asPaddingValues()
                                .calculateBottomPadding()
                        )
                ) {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 4.dp),
                        onClick = {
                            isStartScanning = true
                        },
                    ) {
                        Text("Start", style = MaterialTheme.typography.labelLarge)
                    }
                }
            }
        }
    }
}