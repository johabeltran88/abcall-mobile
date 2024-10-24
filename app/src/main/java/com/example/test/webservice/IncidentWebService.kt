package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Incident
import com.google.gson.Gson
import org.json.JSONObject

class IncidentWebService {
    companion object {
        const val RESOURCE = "/companies/company_id/consumers/consumer_id/pccs"
    }

    fun createPqr(
        incident: Incident,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(AuthWebService.RESOURCE, toJSONObject(incident), responseListener, errorListener)
    }

    private fun toJSONObject(incident: Incident): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(incident)
        return JSONObject(jsonString)
    }
}