package com.inclusitech.safeband.core.data

data class CreateUserApiResponse(
    val status: String,
    val authKey: String,
    val uid: String,
    val email: String
)

data class CreateUserApiRequest(
    val email: String,
    val password: String,
    val name: String,
    val accountType: String
)