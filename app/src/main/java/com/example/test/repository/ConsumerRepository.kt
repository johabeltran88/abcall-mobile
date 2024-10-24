package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.test.model.ConsumerResponse
import com.example.test.network.NetworkAdapterService
import java.util.Locale

class ConsumerRepository(private val application: Application) {
    suspend fun get(token: String?): ConsumerResponse {
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
            NetworkAdapterService.getInstance(application).getConsumer(token)
        } else {
            ConsumerResponse()
        }
    }
}