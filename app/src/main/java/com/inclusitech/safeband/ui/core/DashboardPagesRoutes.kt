package com.inclusitech.safeband.ui.core

sealed class DashboardPagesRoutes(val route: String) {
    data object HomeDashboard : MainRoutes("page-home")
    data object EmergencyDashboard : MainRoutes("page-emergency")
    data object AccountDashboard : MainRoutes("page-account")
}