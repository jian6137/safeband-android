package com.inclusitech.safeband.core.config

class APIConfig {
    companion object {
        private const val PRODUCTION_DOMAIN = "https://api-safeband.zaide.online"
        private const val LOCALHOST_DOMAIN = "http://192.168.1.16:7000"
        const val BASE_URL = "$PRODUCTION_DOMAIN/"

        const val SIGNUP_URL = "api/v1/auth/signup"
        const val LOGIN_URL = "api/v1/auth/login"
        const val USER_PROVISION_URL = "api/v1/getPatientProvision"
        const val GET_LINKED_PATIENTS = "api/v1/user/getLinkedPatientLists"

    }

}