package com.example.test.repository

import android.app.Application
import com.example.test.model.Incident
import com.example.test.network.NetworkAdapterService

class CreateRepository(private val application: Application) {
    suspend fun create(incident: Incident) : String {
        return NetworkAdapterService.getInstance(application).create(incident)
    }
}