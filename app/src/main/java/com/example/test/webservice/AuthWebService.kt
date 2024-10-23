package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Consumer
import com.example.test.model.Incident
import com.google.gson.Gson
import org.json.JSONObject

class AuthWebService {
    companion object {
        const val RESOURCE = "/auth/consumers/token"
        const val RESOURCECREATE = ""
    }

    fun create(
        consumer: Consumer,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(consumer), responseListener, errorListener)
    }

    fun createPqr(
        incident: Incident,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCECREATE, toJSONObject(incident), responseListener, errorListener)
    }

    private fun toJSONObject(obj: Any): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(obj)
        return JSONObject(jsonString)
    }
}