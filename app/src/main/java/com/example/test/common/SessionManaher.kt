package com.example.test.common

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val name = "ABCall-Session"
    val keyToken = "token"

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun addValue(key: String, value: String) {
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String? {
        return sharedPref.getString(key, null)
    }

    fun clearSession() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}
