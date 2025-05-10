package com.inclusitech.safeband.ui.views.onboarding

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
fun FinishSetupView(navHostController: NavHostController, setupVMService: SetupVMService) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.check_anim))
    val progress by animateLottieCompositionAsState(
        composition = composition
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
                    .height(124.dp)
            )
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(64.dp))
            Text(text = "You're Up!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
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
//                        navHostController.navigate(MainRoutes.MainDashboardView.route) {
//                            popUpTo(0) { inclusive = true }
//                            launchSingleTop = true
//                        }
                    },
                ) {
                    Text("Go to Dashboard")
                }
                OutlinedButton(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(start = 4.dp),
                    onClick = {
                        navHostController.navigate(SetupRoutes.FinishSetup.route)
                    },
                ) {
                    Text("Open Getting Started Guide")
                }
            }
        }
    }
}