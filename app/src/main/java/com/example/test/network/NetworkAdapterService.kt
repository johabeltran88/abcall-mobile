package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.test.model.Consumer
import com.example.test.model.Incident
import com.example.test.model.Token
import com.example.test.webservice.AuthWebService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkAdapterService constructor(context: Context) {
    private val authWebService = AuthWebService()

    companion object {
        private var instance: NetworkAdapterService? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkAdapterService(context).also { instance = it }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun login(consumer: Consumer) = suspendCoroutine { continuation ->
        requestQueue.add(authWebService.create(consumer, { response ->
            continuation.resume(
                Token(
                    token = response.getString("token")
                )
            )
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun create(incident: Incident) = suspendCoroutine { continuation ->
        requestQueue.add(authWebService.createPqr(incident, { response ->
            continuation.resume(response.getString("token"))
        }, {
            continuation.resumeWithException(it)
        }))
    }
}