package com.example.jla.domain.model

data class LocationDetails(
    val addressLines: List<String> = listOf(),
    val admin: String? = "",
    val suhAdmin: String? = "",
    val locality: String? = "",
    val thoroughFare: String? = null,
    val postalCode: String? = null,
    val countryCode: String? = "",
    val countryName: String? = "",
    val latitude: String? = "",
    val longitude: String? = "",
)
