package com.inclusitech.safeband.core.data

data class PatientProvisionAPIResponse(
    val status: String,
    val userBound: Boolean,
    val data: String
)

data class FirestoreTimestamp(
    val _seconds: Long,
    val _nanoseconds: Long
)

data class ContactInfo(
    val phoneNumber: String,
    val name: String,
    val photoURL: String
)
