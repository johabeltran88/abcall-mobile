package com.example.test.model

data class Consumer (
    val id: String?,
    val identificationType: String?,
    val identificationNumber: String?,
    val contactNumber: String?,
    val address: String?,
    val companies: List<Company>,
)