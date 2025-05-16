package com.inclusitech.safeband.ui.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.inclusitech.safeband.R
import com.inclusitech.safeband.core.HapticsManager
import com.inclusitech.safeband.core.vms.MainVMService
import com.inclusitech.safeband.ui.views.main.HomeDashboard
import java.util.Calendar

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun DashboardPagesRouter(
    mainNavController: NavHostController,
    mainVMService: MainVMService,
) {
    val navController = rememberNavController()
    val mainContentScrollState = rememberScrollState()
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_home_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_home_outlined)
        ),
        BottomNavigationItem(
            title = "Emergency Info",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_emergency),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_emergency),

            ),
        BottomNavigationItem(
            title = "Account",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_user_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.ic_user_outlined),

            ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.route) {
            DashboardPagesRoutes.HomeDashboard.route -> {
                selectedItemIndex = 0
            }

            DashboardPagesRoutes.EmergencyDashboard.route -> {
                selectedItemIndex = 1
            }

            DashboardPagesRoutes.AccountDashboard.route -> {
                selectedItemIndex = 2
            }
        }
    }

    val topBarVisible = remember { mutableStateOf(true) }
    val animatedAppLogo = AnimatedImageVector.animatedVectorResource(R.drawable.splash_animated)
    val animLogoPainter =
        rememberAnimatedVectorPainter(animatedImageVector = animatedAppLogo, atEnd = true)

    var scaffoldInnerPadding = remember { mutableStateOf<PaddingValues>(PaddingValues(0.dp)) }
    val scaffoldContentPadding = remember { mutableStateOf(0.dp) }

    val surfaceContainerHighest = MaterialTheme.colorScheme.surfaceContainerHighest

    val statusbarBackground = remember { mutableStateOf(surfaceContainerHighest) }

    val haptic = LocalHapticFeedback.current
    LaunchedEffect(mainContentScrollState.value) {
        val scrollThreshold = 10
        val previousScrollValue = mainContentScrollState.value

        snapshotFlow { mainContentScrollState.value }
            .collect { newValue ->
                topBarVisible.value = if (newValue < previousScrollValue - scrollThreshold) {
                    if (!topBarVisible.value) {
                        HapticsManager.triggerHapticFeedback(haptic)
                    }
                    true
                } else if (newValue > previousScrollValue + scrollThreshold) {
                    if (topBarVisible.value) {
                        HapticsManager.triggerHapticFeedback(haptic)
                    }
                    false
                } else {
                    topBarVisible.value
                }
            }
    }

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = WindowInsets.safeGestures.asPaddingValues()
                            .calculateBottomPadding() + 16.dp
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh),
                Arrangement.Center,
                Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            when (selectedItemIndex) {
                                0 -> navController.navigate(DashboardPagesRoutes.HomeDashboard.route)
                                1 -> navController.navigate(DashboardPagesRoutes.EmergencyDashboard.route)
                                else -> navController.navigate(DashboardPagesRoutes.AccountDashboard.route)
                            }
                        },
                        label = {
                            Text(
                                text = item.title,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 1
                            )
                        },
                        alwaysShowLabel = false,
                        icon = {

                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
            }
        },

        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            WindowInsets.systemBars
                                .only(WindowInsetsSides.Top)
                                .asPaddingValues()
                                .calculateTopPadding()
                        )
                        .background(statusbarBackground.value)
                        .zIndex(5f)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(
                            if (selectedItemIndex == 0) if (topBarVisible.value) RoundedCornerShape(
                                0.dp
                            ) else RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            ) else if (selectedItemIndex == 1) RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            ) else RoundedCornerShape(
                                bottomStart = 16.dp,
                                bottomEnd = 16.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                        .zIndex(5f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .aspectRatio(1f)
                                .graphicsLayer {
                                    scaleX = 1.5f
                                    scaleY = 1.5f
                                },
                            painter = animLogoPainter,
                            contentDescription = "SafeBand Icon",
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            "SafeBand",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (selectedItemIndex == 0) {
                        Text(
                            text = "${getTimeOfDayGreeting()}, ${FirebaseAuth.getInstance().currentUser?.displayName?.split(" ")?.get(0)}!",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    } else if (selectedItemIndex == 1) {
                        Text(
                            text = "Notifications",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = "You have no new notifications.",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .padding(top = 2.dp)
                                .padding(start = 16.dp, end = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                AnimatedVisibility(
                    visible = topBarVisible.value,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut()
                ) {
                    if (selectedItemIndex == 0) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                                .zIndex(-2f)
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp),
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .padding(start = 16.dp, end = 16.dp),
                                    onClick = {
                                        mainNavController.navigate(MainRoutes.AddDeviceIntroView.route)
                                    },
                                    colors = CardDefaults.cardColors().copy(
                                        containerColor = MaterialTheme.colorScheme.secondary,
                                        contentColor = MaterialTheme.colorScheme.onSecondary
                                    ),
                                    shape = RoundedCornerShape(24.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .fillMaxHeight()
                                            .padding(16.dp),
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.wristband_add),
                                            contentDescription = "Add Wristband Icon",
                                            modifier = Modifier
                                                .size(32.dp),
                                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondary)
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Text(
                                            text = "Add Device",
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                        Text(
                                            text = "Connect a new SafeBand Device",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSecondary,
                                            modifier = Modifier
                                                .padding(top = 2.dp)
                                                .alpha(0.5f)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    } else if (selectedItemIndex == 1) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                                .zIndex(-2f)
                        ) {

                        }
                    }
                }
            }

        }


    ) { innerPadding ->
        scaffoldInnerPadding.value = innerPadding
        if (scaffoldContentPadding.value == 0.dp) {
            scaffoldContentPadding.value = scaffoldInnerPadding.value.calculateTopPadding()
        }

        NavHost(
            navController = navController,
            startDestination = DashboardPagesRoutes.HomeDashboard.route,
        ) {

            composable(
                route = DashboardPagesRoutes.HomeDashboard.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                HomeDashboard(
                    navController,
                    mainNavController,
                    mainVMService,
                    mainContentScrollState,
                    scaffoldContentPadding.value
                )
            }

            composable(
                route = DashboardPagesRoutes.EmergencyDashboard.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                // TODO Emergency
            }

            composable(
                route = DashboardPagesRoutes.AccountDashboard.route,
                enterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                exitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                },
                popEnterTransition = {
                    fadeIn(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    ) +
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                                initialOffset = { 60 },
                                animationSpec = tween(
                                    durationMillis = 350,
                                    easing = FastOutSlowInEasing
                                )
                            )
                },
                popExitTransition = {
                    fadeOut(
                        animationSpec = tween(
                            durationMillis = 350,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            ) {
                // TODO Account
            }
        }
    }
}

fun getTimeOfDayGreeting(): String {
    val calendar = Calendar.getInstance()
    return when (calendar.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        in 17..20 -> "Good Evening"
        in 21..23 -> "Good Night"
        else -> "Hello"
    }
}

