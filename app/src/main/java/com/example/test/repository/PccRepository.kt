package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.test.model.Pcc
import com.example.test.network.NetworkAdapterService
import java.util.Locale

class PccRepository(private val application: Application) {
    suspend fun createPcc(token: String, companyId: String, consumerId: String, pcc: Pcc): Pcc {
        return NetworkAdapterService.getInstance(application)
            .createPcc(token, companyId, consumerId, pcc)
    }

    suspend fun getAll(token: String): List<Pcc> {
        val network = NetworkAdapterService.getInstance(application)
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
            val listPcc = NetworkAdapterService.getInstance(application).getConsumerByToken(token).pccs
            return listPcc
        } else {
            return emptyList<Pcc>()
        }
    }

    suspend fun get(): Pcc{
        return try {
            Pcc(
                subject = "",
                description = ""
            )
        } catch (e: Exception) {
            Pcc(subject = "", description = "")
        }
    }
}