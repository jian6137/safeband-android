package com.inclusitech.safeband.ui.core

sealed class MainRoutes(val route: String) {
    data object HomeDashboard : MainRoutes("home")
    data object AddDeviceIntroView: MainRoutes("add-device-intro")
    data object AddNewDeviceView: MainRoutes("add-new-device")
    data object AddDeviceCustomize: MainRoutes("add-device-customize")
    data object ConnectDeviceNFC: MainRoutes("add-device-nfc")
    data object ConnectDeviceBluetooth: MainRoutes("add-device-bt")
    data object ConnectDevicePicker: MainRoutes("add-device-picker")
    data object DeviceAddComplete: MainRoutes("add-device-complete")

    data object GenericErrorView: MainRoutes("error")

}