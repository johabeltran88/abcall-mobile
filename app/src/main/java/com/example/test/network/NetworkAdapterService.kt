package com.example.test.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.test.model.Company
import com.example.test.model.Consumer
import com.example.test.model.Login
import com.example.test.model.Pcc
import com.example.test.webservice.AuthWebService
import com.example.test.webservice.ConsumerWebService
import com.example.test.webservice.PccWebService
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkAdapterService constructor(context: Context) {
    private val authWebService = AuthWebService()
    private val consumerWebService = ConsumerWebService()
    private val pccWebService = PccWebService()

    companion object {
        private var instance: NetworkAdapterService? = null
        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: NetworkAdapterService(context).also { instance = it }
        }
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    suspend fun login(login: Login): String = suspendCoroutine { continuation ->
        requestQueue.add(authWebService.create(login, { response ->
            continuation.resume(response.getString("token"))
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun getConsumerByToken(token: String): Consumer = suspendCoroutine { continuation ->
        requestQueue.add(consumerWebService.getByToken(token, { response ->
            val companiesJsonArray = JSONObject(response).getJSONArray("companies")
            val companies = mutableListOf<Company>()
            for (i in 0 until companiesJsonArray.length()) {
                val companyJson = companiesJsonArray.getJSONObject(i)
                val company = Company(
                    id = companyJson.getString("id"),
                    name = companyJson.getString("name"),
                )
                companies.add(company)
            }
            val pccsJsonArray = JSONObject(response).getJSONArray("pccs")
            val pccs = mutableListOf<Pcc>()
            for (i in 0 until pccsJsonArray.length()) {
                val pccJson = pccsJsonArray.getJSONObject(i)

                val companyJson = pccJson.getJSONObject("company")
                val company = Company(
                    id = companyJson.getString("id"),
                    name = companyJson.getString("name")
                )

                val pcc = Pcc(
                    id = pccJson.getString("id"),
                    subject = pccJson.getString("subject"),
                    description = pccJson.getString("description"),
                    status = pccJson.getString("status"),
                    company = company
                )
                pccs.add(pcc)
            }
            continuation.resume(
                Consumer(
                    id = JSONObject(response).getString("id"),
                    identificationType = JSONObject(response).getString("identification_type"),
                    identificationNumber = JSONObject(response).getString("identification_number"),
                    contactNumber = JSONObject(response).getString("contact_number"),
                    address = JSONObject(response).getString("address"),
                    companies = companies,
                    pccs = pccs
                )
            )
        }, {
            continuation.resumeWithException(it)
        }))
    }

    suspend fun createPcc(token: String, companyId: String, consumerId: String, pcc: Pcc): Pcc =
        suspendCoroutine { continuation ->
            requestQueue.add(pccWebService.create(token, companyId, consumerId, pcc, { response ->
                continuation.resume(
                    Pcc(
                        id = response.getString("id"),
                        subject = response.getString("subject"),
                        description = response.getString("description"),
                        status = response.getString("status"),
                    )
                )
            }, {
                continuation.resumeWithException(it)
            }))
        }
}