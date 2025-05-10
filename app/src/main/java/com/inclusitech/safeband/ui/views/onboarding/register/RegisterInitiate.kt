package com.inclusitech.safeband.ui.views.onboarding.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.firebase.auth.FirebaseAuth
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.vms.SetupVMService
import kotlinx.coroutines.tasks.await
import androidx.compose.runtime.rememberCoroutineScope
import com.inclusitech.safeband.ui.core.SetupRoutes
import kotlinx.coroutines.launch

@Composable
fun RegisterInitiate(navHostController: NavHostController, setupVMService: SetupVMService) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_anim))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val firebaseAuth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        setupVMService.createUser(
            onDataFetched = { data ->
                when (data.status) {
                    "EMAIL_ALREADY_IN_USE" -> {
                        navHostController.popBackStack()
                        navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Error!/The email you are trying to use is already existing. Please use a different email.")
                    }

                    "OK" -> {
                        val token = data.authKey
                        coroutineScope.launch {
                            try {
                                firebaseAuth.signInWithCustomToken(token).await()

                            } catch (e: Exception) {
                                e.printStackTrace()
                                navHostController.popBackStack()
                                navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Internal Error!/Something went wrong and the system failed to authenticate. Please try again or Contact Us.")
                            }
                        }
                    }

                    else -> {
                        navHostController.popBackStack()
                        navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Internal Error!/Something went wrong and the system can't create your account. Please try again or Contact Us.")
                    }
                }
            },
            onError = {
                navHostController.popBackStack()
                navHostController.navigate("${SetupRoutes.ErrorInfoView.route}/Internal Error!/Something went wrong and the system can't create your account. Please try again or Contact Us.")
            }
        )
    }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = {
                    progress
                },
                modifier = Modifier.size(300.dp)
            )
        }
    }
}