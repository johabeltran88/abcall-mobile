package com.example.test.common

import android.content.Context

fun saveToSession(context: Context, key: String, value: String?) {
    val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun getSession(context: Context, key: String): String?{
    val sharedPreferences = context.getSharedPreferences("SessionPreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, null)
}