package com.inclusitech.safeband.core.data

data class LinkedPatientsResponse(
    val status: String,
    val data: String,
)

data class LinkedPatientInfo(
    val id: String,
    val name: String,
    val photoURL: String
)