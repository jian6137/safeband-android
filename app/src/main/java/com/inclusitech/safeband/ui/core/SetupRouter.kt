package com.inclusitech.safeband.ui.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.composable
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import com.inclusitech.safeband.core.vms.SetupVMService
import com.inclusitech.safeband.ui.views.onboarding.RequestCameraPermissionView
import com.inclusitech.safeband.ui.views.onboarding.AuthenticationView
import com.inclusitech.safeband.ui.views.onboarding.ErrorInfo
import com.inclusitech.safeband.ui.views.onboarding.FinishSetupView
import com.inclusitech.safeband.ui.views.onboarding.LoadingView
import com.inclusitech.safeband.ui.views.onboarding.SelectPhotoView
import com.inclusitech.safeband.ui.views.UserConsentSetup
import com.inclusitech.safeband.ui.views.onboarding.UserProvisionView
import com.inclusitech.safeband.ui.views.onboarding.WelcomeView
import com.inclusitech.safeband.ui.views.onboarding.register.CaregiverAccountSetupView
import com.inclusitech.safeband.ui.views.onboarding.register.RegisterAccountView
import com.inclusitech.safeband.ui.views.onboarding.register.RegisterInitiate
import com.inclusitech.safeband.ui.views.onboarding.register.RegisterIntroView

@Composable
fun SetupRouter(setupVMService: SetupVMService) {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = SetupRoutes.Loading.route
    ) {

        composable(
            route = SetupRoutes.WelcomeScreen.route,
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
            WelcomeView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.Loading.route,
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
            LoadingView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.SelectPhoto.route,
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
            SelectPhotoView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.RequestCameraPermission.route,
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
            RequestCameraPermissionView(navController, setupVMService)
        }

        composable(
            route = "${SetupRoutes.UserProvisionView.route}/{patientID}",
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
            val patientID = navBackStackEntry.arguments?.getString("patientID")
            patientID?.let {
                UserProvisionView(
                    navHostController = navController,
                    setupVMService = setupVMService,
                    patientID = patientID
                )
            }
        }

        composable(
            route = SetupRoutes.UserConsentSetup.route,
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
            UserConsentSetup(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.AuthenticationSetup.route,
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
            AuthenticationView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.FinishSetup.route,
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
            FinishSetupView(navController, setupVMService)
        }

        composable(
            route = "${SetupRoutes.ErrorInfoView.route}/{errorTitle}/{errorMessage}",
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
            route = SetupRoutes.RegisterGettingStarted.route,
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
            RegisterIntroView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.RegisterCaregiverAccountPreview.route,
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
            CaregiverAccountSetupView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.RegisterAccountView.route,
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
            RegisterAccountView(navController, setupVMService)
        }

        composable(
            route = SetupRoutes.RegisterInitiate.route,
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
            RegisterInitiate(navController, setupVMService)
        }
    }
}