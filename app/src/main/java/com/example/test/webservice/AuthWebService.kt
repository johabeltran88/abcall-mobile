package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Consumer
import com.google.gson.Gson
import org.json.JSONObject

class AuthWebService {
    companion object {
        const val RESOURCE = "/auth/consumers/token"
    }

    fun create(
        consumer: Consumer,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(consumer), responseListener, errorListener)
    }

    private fun toJSONObject(consumer: Consumer): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(consumer)
        return JSONObject(jsonString)
    }
}