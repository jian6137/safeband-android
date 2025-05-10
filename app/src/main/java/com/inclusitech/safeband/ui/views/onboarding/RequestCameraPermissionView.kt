package com.inclusitech.safeband.ui.views.onboarding

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.inclusitech.safeband.R
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.core.SetupRoutes

@Composable
fun RequestCameraPermissionView(navHostController: NavHostController, setupVMService: SetupVMService) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.req_cam_perm))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    val context = LocalContext.current

    fun proceed(){
        navHostController.navigate(SetupRoutes.SelectPhoto.route)
    }

    var permissionResultStatus by remember { mutableStateOf("INIT") }
    var permissionStatus by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            permissionStatus = true
            proceed()
        } else {
            permissionResultStatus = "NOT_GRANTED"
            Toast.makeText(context, "Camera permission is required to continue.", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED) {
            if(permissionResultStatus == "CONTINUE") {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                if(permissionStatus){
                    proceed()
                }
            }
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
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Heads Up!", style = MaterialTheme.typography.displaySmall)
            if(permissionResultStatus == "INIT"){
                Text(
                    text = "To proceed, we need access to your device's camera. This is necessary to scan the setup QR code provided by your patient's doctor. Granting camera permission ensures a smooth and secure onboarding process.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            } else if (permissionResultStatus === "NOT_GRANTED"){
                Text(
                    text = "Permission was not granted. Please Grant it in the app's Permission Settings to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp, start = 16.dp, end = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            } else if(permissionResultStatus === "CONTINUE"){
                if(permissionStatus){
                    Text(
                        text = "Permission was granted!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(
                        text = "Permission was not granted. Please Grant it in the app's Permission Settings to continue",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
            }

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
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    onClick = {
                        if(permissionResultStatus === "INIT"){
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        } else if(permissionResultStatus === "NOT_GRANTED"){
                            permissionResultStatus = "CONTINUE"
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", context.packageName, null)
                            intent.data = uri
                            context.startActivity(intent)
                        } else if(permissionResultStatus === "CONTINUE"){
                            if(!permissionStatus){
                                permissionResultStatus = "CONTINUE"
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context.packageName, null)
                                intent.data = uri
                                context.startActivity(intent)
                            } else {
                                proceed()
                            }
                        }
                    },
                ) {
                    if(permissionResultStatus === "INIT"){
                        Text("Grant", style = MaterialTheme.typography.labelLarge)
                    } else if(permissionResultStatus === "NOT_GRANTED"){
                        Text("Open Settings", style = MaterialTheme.typography.labelLarge)
                    } else if(permissionResultStatus === "CONTINUE"){
                        if(permissionStatus){
                            Text("Continue", style = MaterialTheme.typography.labelLarge)
                        } else {
                            Text("Open Settings", style = MaterialTheme.typography.labelLarge)
                        }
                    }
                }
            }
        }
    }
}