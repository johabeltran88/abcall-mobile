package com.example.test.webservice

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.test.common.postRequest
import com.example.test.model.Login
import com.google.gson.Gson
import org.json.JSONObject

class AuthWebService {
    companion object {
        const val RESOURCE = "/auth/consumers/token"
    }

    fun create(
        login: Login,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return postRequest(RESOURCE, toJSONObject(login), responseListener, errorListener)
    }

    private fun toJSONObject(login: Login): JSONObject {
        val gson = Gson()
        val jsonString = gson.toJson(login)
        return JSONObject(jsonString)
    }
}