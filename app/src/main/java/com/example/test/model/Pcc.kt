package com.example.test.model

data class Pcc(
    val id: String?,
    val subject: String?,
    val description: String?,
    val status: String?,
    val company: Company?
) {
    constructor(subject: String?, description: String?) : this(
        id = null,
        subject = subject,
        description = description,
        status = null,
        company = null
    )

    constructor(id: String?, subject: String?, description: String?, status: String?) : this(
        id = id,
        subject = subject,
        description = description,
        status = status,
        company = null
    )
}