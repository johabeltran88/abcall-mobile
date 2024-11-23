package com.example.test.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.test.model.Notification
import com.example.test.model.Pcc
import com.example.test.network.NetworkAdapterService

class NotificationRepository(private val application: Application) {
    suspend fun getNotificationAll(token: String, pccId: String): List<Notification>{
        val network = NetworkAdapterService.getInstance(application)
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true ||
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true) {
            val listNotifications = network.getNotificationByPcc(token, pccId).notification?: emptyList()
            return listNotifications
        } else {
            return emptyList<Notification>()
        }
    }
}