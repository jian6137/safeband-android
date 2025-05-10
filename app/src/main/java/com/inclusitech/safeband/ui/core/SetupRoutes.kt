package com.inclusitech.safeband.ui.core

sealed class SetupRoutes(val route: String) {
    data object WelcomeScreen : SetupRoutes("welcome-screen")
    data object Loading : SetupRoutes("loading")
    data object RequestCameraPermission : SetupRoutes("request-camera-permission")
    data object UserProvisionView : SetupRoutes("user-provision")
    data object SelectPhoto : SetupRoutes("select-photo")
    data object UserConsentSetup : SetupRoutes("user-consent-setup")
    data object AuthenticationSetup : SetupRoutes("authentication-setup")
    data object FinishSetup : SetupRoutes("finish-setup")
    data object ErrorInfoView : SetupRoutes("finish-setup")

    data object RegisterGettingStarted : SetupRoutes("acc-reg-get-started")
    data object RegisterCaregiverAccountPreview : SetupRoutes("acc-caregiver-setup-preview")
    data object RegisterAccountView : SetupRoutes("acc-setup")
    data object RegisterInitiate : SetupRoutes("init-registration")

}