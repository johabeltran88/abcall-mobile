package com.example.test.common

import android.content.Context
import com.example.test.R

fun validateValue(field: String?, maxLength: Int, context: Context): String {
    if (field.isNullOrBlank())
        return context.getString(R.string.error1)
    if (maxLength != -1 && field.length > maxLength)
        return context.getString(R.string.error3, maxLength)
    return ""
}
fun validateEmail(field: String?, context: Context): String {
    if (field.isNullOrBlank()) {
        return context.getString(R.string.error1)
    } else {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

        if (!emailRegex.matches(field))
            return context.getString(R.string.error2)
        return ""
    }
}

fun validateFieldString(field: String?, minLength: Int, maxLength: Int, context: Context): String {
    if (field.isNullOrBlank())
        return context.getString(R.string.error1)
    if (field.length < minLength || field.length > maxLength)
        return context.getString(R.string.error5, minLength, maxLength)
    return ""
}