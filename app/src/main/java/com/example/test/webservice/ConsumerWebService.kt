package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.example.test.common.getRequest
import com.example.test.common.postRequest
import com.example.test.model.Incident
import com.google.gson.Gson
import org.json.JSONObject

class ConsumerWebService {
    companion object {
        const val RESOURCE = "/consumers"
    }

    fun get(
        token: String?,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return getRequest(ConsumerWebService.RESOURCE, token, responseListener, errorListener)
    }

    private fun toJSONObject(incident: Incident): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(incident)
        return JSONObject(jsonString)
    }
}