package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.test.model.Consumer
import com.example.test.model.ConsumerResponse
import com.example.test.model.Incident
import com.example.test.model.Token
import com.example.test.webservice.AuthWebService
import com.example.test.webservice.ConsumerWebService
import com.example.test.webservice.IncidentWebService
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import org.json.JSONObject

class NetworkAdapterService constructor(context: Context) {
    private val authWebService = AuthWebService()
    private val incidentWebService = IncidentWebService()
    private val consumerWebService = ConsumerWebService()

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
        requestQueue.add(incidentWebService.createPqr(incident, { response ->
            continuation.resume(response.getString("token"))
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun getConsumer(token: String?) = suspendCoroutine<ConsumerResponse> { continuation ->
        requestQueue.add(consumerWebService.get(token, { response ->
            try {
                // Crear una instancia vacía de ConsumerResponse
                val consumerResponse = ConsumerResponse()

                // Iterar sobre las claves del JSON y rellenar dinámicamente
                val jsonObject = JSONObject(response)
                for (key in jsonObject.keys()) {
                    val value = jsonObject.getString(key)
                    // Usar reflexión para asignar valores dinámicamente
                    val field = ConsumerResponse::class.java.getDeclaredField(key)
                    field.isAccessible = true
                    field.set(consumerResponse, value)
                }

                // Retornar la estructura ConsumerResponse
                continuation.resume(consumerResponse)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }, {
            continuation.resumeWithException(it)
        }))
    }
}