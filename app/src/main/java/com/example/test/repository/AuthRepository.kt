package com.example.test.repository

import android.app.Application
import com.example.test.model.Login
import com.example.test.network.NetworkAdapterService

class AuthRepository(private val application: Application) {
    suspend fun login(login: Login) : String {
        return NetworkAdapterService.getInstance(application).login(login)
    }
}