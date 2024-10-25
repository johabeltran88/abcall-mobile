package com.example.test.repository

import android.app.Application
import com.example.test.model.Pcc
import com.example.test.network.NetworkAdapterService

class PccRepository(private val application: Application) {
    suspend fun createPcc(token: String, companyId: String, consumerId: String, pcc: Pcc): Pcc {
        return NetworkAdapterService.getInstance(application)
            .createPcc(token, companyId, consumerId, pcc)
    }
}