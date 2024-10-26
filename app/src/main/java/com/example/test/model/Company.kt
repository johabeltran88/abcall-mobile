package com.example.test.model

data class Company(
    val id: String?,
    val name: String,
) {
    override fun toString(): String {
        return name
    }
}
