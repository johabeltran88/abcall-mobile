package com.example.test.repository

import android.app.Application
import com.example.test.model.Consumer
import com.example.test.model.Token
import com.example.test.network.NetworkAdapterService

class AuthRepository(private val application: Application) {
    suspend fun login(consumer: Consumer) : Token {
        return NetworkAdapterService.getInstance(application).login(consumer)
    }
}