package com.inclusitech.safeband.ui.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.ui.views.main.AddDeviceCustomize
import com.inclusitech.safeband.ui.views.main.AddDeviceIntro
import com.inclusitech.safeband.ui.views.main.AddNewDeviceView
import com.inclusitech.safeband.ui.views.main.ConnectDeviceBT
import com.inclusitech.safeband.ui.views.main.ConnectDeviceNFC
import com.inclusitech.safeband.ui.views.main.ConnectDevicePicker
import com.inclusitech.safeband.ui.views.main.DeviceAddComplete
import com.inclusitech.safeband.ui.views.onboarding.ErrorInfo

@Composable
fun MainRouter(mainVMService: MainVMService) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = MainRoutes.HomeDashboard.route
    ) {

        composable(
            route = MainRoutes.HomeDashboard.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            DashboardPagesRouter(navController, mainVMService)
        }

        composable(
            route = MainRoutes.AddDeviceIntroView.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            AddDeviceIntro(navController, mainVMService)
        }

        composable(
            route = MainRoutes.AddNewDeviceView.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            AddNewDeviceView(navController, mainVMService)
        }

        composable(
            route = MainRoutes.AddDeviceCustomize.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            AddDeviceCustomize(navController, mainVMService)
        }

        composable(
            route = "${MainRoutes.ConnectDeviceNFC.route}/{type}/{patientUID}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) { navBackStackEntry ->
            val pType = navBackStackEntry.arguments?.getString("type")
            val pPatientUID = navBackStackEntry.arguments?.getString("patientUID")
            pType?.let {
                ConnectDeviceNFC(
                    navHostController = navController,
                    mainVMService = mainVMService,
                    type = pType,
                    patientUID = pPatientUID
                )
            }
        }

        composable(
            route = "${MainRoutes.GenericErrorView.route}/{errorTitle}/{errorMessage}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) { navBackStackEntry ->
            val errorTitle = navBackStackEntry.arguments?.getString("errorTitle")
            val errorMessage = navBackStackEntry.arguments?.getString("errorMessage")
            errorTitle?.let {
                errorMessage?.let {
                    ErrorInfo(
                        navHostController = navController,
                        errorTitle = errorTitle,
                        errorMessage = errorMessage
                    )
                }
            }
        }

        composable(
            route = "${MainRoutes.ConnectDeviceBluetooth.route}/{type}/{patientUID}",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) { navBackStackEntry ->
            val pType = navBackStackEntry.arguments?.getString("type")
            val pPatientUID = navBackStackEntry.arguments?.getString("patientUID")
            pType?.let {
                ConnectDeviceBT(
                    navHostController = navController,
                    mainVMService = mainVMService,
                    type = pType,
                    patientUID = pPatientUID
                )
            }
        }

        composable(
            route = MainRoutes.ConnectDevicePicker.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            ConnectDevicePicker(
                navHostController = navController,
                mainVMService = mainVMService
            )
        }

        composable(
            route = MainRoutes.DeviceAddComplete.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            },
            exitTransition = {
                scaleOut(
                    targetScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popEnterTransition = {
                scaleIn(
                    initialScale = 0.9f, animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(
                        durationMillis = 350,
                        easing = FastOutSlowInEasing
                    ),
                )
            }
        ) {
            DeviceAddComplete(
                navHostController = navController,
                mainVMService = mainVMService
            )
        }
    }
}
