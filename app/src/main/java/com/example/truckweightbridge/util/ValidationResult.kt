package com.example.truckweightbridge.util

data class ValidationResult(
    val isDateTimeValid: Boolean = true,
    val isDriverNameValid: Boolean = true,
    val isLicenceNumberValid: Boolean = true,
    val isInboundWeightValid: Boolean = true,
    val isOutboundWeightValid: Boolean = true
)