package com.example.test.repository

import android.app.Application
import com.example.test.model.Consumer
import com.example.test.network.NetworkAdapterService

class ConsumerRepository(private val application: Application) {
    suspend fun getConsumerByToken(token: String): Consumer {
        return NetworkAdapterService.getInstance(application).getConsumerByToken(token)
    }
}