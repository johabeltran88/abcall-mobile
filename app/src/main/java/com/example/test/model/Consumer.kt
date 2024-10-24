package com.example.test.model

data class Consumer(
    val email: String?,
    val password: String?,
)

data class ConsumerResponse(
    val identification_type: String? = null,
    val identification_number: String? = null,
    val companies: List<Company>? = null
)