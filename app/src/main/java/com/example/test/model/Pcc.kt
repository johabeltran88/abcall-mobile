package com.example.test.model

data class Pcc(
    val id: String?,
    val subject: String?,
    val description: String?,
    val status: String?,
    val company: Company?,
    val notification: List<Notification>?
) {
    constructor(subject: String?, description: String?) : this(
        id = null,
        subject = subject,
        description = description,
        status = null,
        company = null,
        notification = null
    )

    constructor(id: String?, subject: String?, description: String?, status: String?) : this(
        id = id,
        subject = subject,
        description = description,
        status = status,
        company = null,
        notification = null
    )

    constructor(id: String?, subject: String?, description: String?, status: String?, company: Company?) : this(
        id = id,
        subject = subject,
        description = description,
        status = status,
        company = company,
        notification = null
    )

    constructor(id: String?, subject: String?, description: String?, status: String?, notification: List<Notification>?) : this(
        id = id,
        subject = subject,
        description = description,
        status = status,
        company = null,
        notification = notification
    )
}